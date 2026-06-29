package com.example.expensetracker.presentation.components.expense

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.ExpenseGroup
import com.example.expensetracker.domain.model.expense.ExpenseGroupKey
import com.example.expensetracker.presentation.components.category.CategoryPill
import com.example.expensetracker.presentation.month.detail.MonthDetailEvent

@Composable
fun DayGroupedExpensesList(
    expenseGroups: List<ExpenseGroup>,
    onEvent: (MonthDetailEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        expenseGroups.forEach { group ->

            val key = group.key as? ExpenseGroupKey.DateGroup ?: return@forEach

            item(key = "header_${key.date}") {
                DayHeader(
                    day = key.date,
                    total = group.total
                )
            }

            itemsIndexed(
                items = group.expenses,
                key = { _, expense -> expense.id }
            ) { index, expense ->

                ExpenseCardItem(
                    expense = expense,
                    isFirst = index == 0,
                    isLast = index == group.expenses.lastIndex,
                    onClickExpense = {
                        onEvent(MonthDetailEvent.OnClickExpense(expense))
                    },
                    secondaryContent = {
                        CategoryPill(
                            name = expense.subcategory.name,
                            color = expense.subcategory.color
                        )
                    }
                )
            }
        }
    }
}