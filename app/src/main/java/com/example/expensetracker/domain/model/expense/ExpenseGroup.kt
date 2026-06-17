package com.example.expensetracker.domain.model.expense


data class ExpenseGroup(
    val key: ExpenseGroupKey,
    val expenses: List<Expense>,
    val total: Double = expenses.sumOf { it.amount }
)