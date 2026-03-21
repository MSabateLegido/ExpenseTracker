package com.example.expensetracker.domain.model.expense

sealed class ExpenseRowUiModel {
    data class Header(
        val title: String,
        val total: Double
    ) : ExpenseRowUiModel()

    data class Item(
        val expense: Expense,
        val isFirst: Boolean,
        val isLast: Boolean
    ) : ExpenseRowUiModel()
}