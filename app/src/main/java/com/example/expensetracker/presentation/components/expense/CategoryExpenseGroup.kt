package com.example.expensetracker.presentation.components.expense

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.presentation.components.category.CategoryHeader
import com.example.expensetracker.presentation.month.detail.MonthDetailEvent

@Composable
fun CategoryExpenseGroup(
    title: String,
    total: Double,
    expenses: List<Expense>,
    onEvent: (MonthDetailEvent) -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        CategoryHeader(
            title = title,
            total = total,
            onClick = {
                expanded = !expanded
            }
        )

        AnimatedVisibility(expanded) {
            Column {
                expenses.forEach { expense ->
                    ExpenseCategoryItem(
                        expense = expense,
                        onClick = {
                            onEvent(MonthDetailEvent.OnClickExpense(expense))
                        }
                    )
                }
            }
        }
    }
}