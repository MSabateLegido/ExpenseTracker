package com.example.expensetracker.presentation.month.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.ExpenseGroupBy
import com.example.expensetracker.domain.model.expense.ExpenseOrderBy
import com.example.expensetracker.presentation.components.expense.ExpenseBottomSheet
import com.example.expensetracker.presentation.components.expense.ExpensesList
import com.example.expensetracker.presentation.components.month.MonthTitle
import com.example.expensetracker.utils.formatMonthYear


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
                ExpenseGroupBy.Subcategory -> "Category"
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
                    onGroupSelected(ExpenseGroupBy.Subcategory)
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
        is ExpenseOrderBy.Alphabetical -> "A-Z"
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