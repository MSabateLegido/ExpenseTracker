package com.example.expensetracker.presentation.month.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.expensetracker.presentation.expenses.add.limitTwoDecimals
import com.example.expensetracker.presentation.components.category.CategoryPill
import com.example.expensetracker.presentation.components.category.CategorySelectorDropdown
import com.example.expensetracker.presentation.components.expense.ExpenseDateField
import com.example.expensetracker.presentation.components.expense.ExpenseListByCategory
import com.example.expensetracker.presentation.components.expense.ExpenseListByDay
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

        Spacer(Modifier.height(32.dp))

        when (state.uiState) {
            ExpenseListUiState.Loading -> {

            }
            is ExpenseListUiState.ByDay ->{
                ExpenseListByDay(
                    items = state.uiState.items,
                    onClickExpense = { onEvent(MonthDetailEvent.OnClickExpense(it)) }
                )
            }
            is ExpenseListUiState.ByCategory -> {
                ExpenseListByCategory(
                    categories = state.uiState.categories,
                    onClickExpense = { onEvent(MonthDetailEvent.OnClickExpense(it)) },
                    onClickSubcategory = { onEvent(MonthDetailEvent.OnClickSubcategory(it)) }
                )
            }
        }

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
fun MonthTitle(
    title: String,
    onNextMonth: () -> Unit,
    onPreviousMonth: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onPreviousMonth }
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(90f),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { onNextMonth }
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(90f),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseBottomSheet(
    expense: Expense,
    categories: List<CategoryWithChildren>,
    onEvent: (MonthDetailEvent) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var hasChanges by remember(expense.id) { mutableStateOf(false) }

    var title by remember(expense.id) { mutableStateOf(expense.title) }
    var amount by remember(expense.id) { mutableStateOf(expense.amount.toString()) }
    var category by remember(expense.id) { mutableStateOf(expense.category) }
    var date by remember(expense.id) { mutableStateOf(expense.date) }

    ModalBottomSheet(
        onDismissRequest = {
            if (hasChanges) {
                onEvent(MonthDetailEvent.UpdateExpense(
                    expense.copy(
                        title = title,
                        amount = amount.toDouble(),
                        category = category,
                        date = date
                    )
                ))
            }
            onEvent(MonthDetailEvent.DismissBottomSheet)
        },
        sheetState = sheetState,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .navigationBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Editar despesa",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    hasChanges = true
                },
                label = { Text("Títol") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    limitTwoDecimals(it) {
                        amount = it
                    }
                    hasChanges = true
                },
                label = { Text("Import") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Decimal
                )
            )

            CategorySelectorDropdown(
                modifier = Modifier.fillMaxWidth(),
                categories = categories,
                selectedSubcategory = category,
                onSubcategorySelected = {
                    category = it
                    hasChanges = true
                }
            )

            ExpenseDateField(
                date = date,
                onDateSelected = {
                    date = it
                    hasChanges = true
                }
            )

            Spacer(Modifier.height(24.dp))

            HorizontalDivider()

            TextButton(
                onClick = {
                    hasChanges = false
                    onEvent(MonthDetailEvent.DeleteExpense(expense))
                    onEvent(MonthDetailEvent.DismissBottomSheet)
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar despesa")
            }
        }
    }
}

