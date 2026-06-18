package com.example.expensetracker.presentation.month.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.category.CategoryWithChildren
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.expense.ExpenseGroupBy
import com.example.expensetracker.domain.model.expense.ExpenseOrderBy
import com.example.expensetracker.presentation.expenses.add.limitTwoDecimals
import com.example.expensetracker.presentation.components.category.CategoryPill
import com.example.expensetracker.presentation.components.category.CategorySelectorDropdown
import com.example.expensetracker.presentation.components.expense.ExpenseBottomSheet
import com.example.expensetracker.presentation.components.expense.ExpenseDateField
import com.example.expensetracker.presentation.components.expense.ExpensesList
import com.example.expensetracker.presentation.components.month.MonthTitle
import com.example.expensetracker.utils.formatAmount
import com.example.expensetracker.utils.formatMonthYear
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun MonthDetailScreen(
    modifier: Modifier = Modifier,
    state: MonthDetailState,
    onEvent: (MonthDetailEvent) -> Unit
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        MonthTitle(
            title = state.yearMonth.formatMonthYear(),
            onNextMonth = { onEvent(MonthDetailEvent.OnNextMonth) },
            onPreviousMonth = { onEvent(MonthDetailEvent.OnPreviousMonth) }
        )

        GroupAndOrderSelectors(
            groupBy = state.groupBy,
            orderBy = state.orderBy,
            onEvent = onEvent
        )

        ExpensesList(
            expenseGroups = state.expenseGroups,
            groupBy = state.groupBy,
            onEvent = onEvent
        )
    }

    if (state.selectedExpense != null) {
        ExpenseBottomSheet(
            expense = state.selectedExpense,
            onEvent = onEvent,
            categories = state.categories
        )
    }
}

@Composable
fun GroupAndOrderSelectors(
    groupBy: ExpenseGroupBy,
    orderBy: ExpenseOrderBy,
    onEvent: (MonthDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GroupSelector(
            modifier = Modifier.weight(1f),
            groupBy = groupBy,
            onGroupSelected = {
                onEvent(MonthDetailEvent.ChangeGroupBy(it))
            }
        )

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OrderSelector(
                modifier = Modifier.weight(1f),
                orderBy = orderBy,
                groupBy = groupBy,
                onOrderSelected = {
                    onEvent(MonthDetailEvent.ChangeOrderBy(it))
                }
            )

            OrderDirectionToggle(
                ascending = orderBy.isAscending(),
                onClick = {
                    onEvent(
                        MonthDetailEvent.ChangeOrderBy(
                            orderBy.toggleDirection()
                        )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSelector(
    groupBy: ExpenseGroupBy,
    onGroupSelected: (ExpenseGroupBy) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = when (groupBy) {
                ExpenseGroupBy.Day -> "Date"
                ExpenseGroupBy.Category -> "Category"
            },
            onValueChange = {},
            readOnly = true,
            label = { Text("Group") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Date") },
                onClick = {
                    onGroupSelected(ExpenseGroupBy.Day)
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("Category") },
                onClick = {
                    onGroupSelected(ExpenseGroupBy.Category)
                    expanded = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSelector(
    orderBy: ExpenseOrderBy,
    groupBy: ExpenseGroupBy,
    onOrderSelected: (ExpenseOrderBy) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = orderBy.toLabel(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Order") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            groupBy.options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(option.toLabel())
                    },
                    onClick = {
                        onOrderSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun OrderDirectionToggle(
    ascending: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier.size(56.dp)
    ) {
        Icon(
            imageVector = if (ascending) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            },
            contentDescription = null
        )
    }
}

fun ExpenseOrderBy.toLabel(): String {
    return when (this) {
        is ExpenseOrderBy.Date -> "Date"
        is ExpenseOrderBy.Total -> "Total"
        is ExpenseOrderBy.Alphabetical -> "Alphabetical"
    }
}

fun ExpenseOrderBy.isAscending(): Boolean {
    return when (this) {
        is ExpenseOrderBy.Date -> ascending
        is ExpenseOrderBy.Total -> ascending
        is ExpenseOrderBy.Alphabetical -> ascending
    }
}

fun ExpenseOrderBy.toggleDirection(): ExpenseOrderBy {
    return when (this) {
        is ExpenseOrderBy.Date ->
            copy(ascending = !ascending)

        is ExpenseOrderBy.Total ->
            copy(ascending = !ascending)

        is ExpenseOrderBy.Alphabetical ->
            copy(ascending = !ascending)
    }
}