package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.expense.Expense

data class MonthDetailState(
    val expenses: List<Expense> = emptyList()
)