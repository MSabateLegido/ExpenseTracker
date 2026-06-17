package com.example.expensetracker.domain.model.expense

sealed interface ExpenseGroupBy {
    data object Day : ExpenseGroupBy
    data object Category : ExpenseGroupBy
}