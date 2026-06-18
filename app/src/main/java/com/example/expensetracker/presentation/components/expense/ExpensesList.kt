package com.example.expensetracker.presentation.components.expense

import androidx.compose.runtime.Composable
import com.example.expensetracker.domain.model.expense.ExpenseGroup
import com.example.expensetracker.domain.model.expense.ExpenseGroupBy
import com.example.expensetracker.presentation.month.detail.MonthDetailEvent

@Composable
fun ExpensesList(
    expenseGroups: List<ExpenseGroup>,
    groupBy: ExpenseGroupBy,
    onEvent: (MonthDetailEvent) -> Unit
) {
    when (groupBy) {
        ExpenseGroupBy.Day -> {
            DayGroupedExpensesList(
                expenseGroups = expenseGroups,
                onEvent = onEvent
            )
        }

        ExpenseGroupBy.Category -> {
            CategoryGroupedExpensesList(
                expenseGroups = expenseGroups,
                onEvent = onEvent
            )
        }
    }
}