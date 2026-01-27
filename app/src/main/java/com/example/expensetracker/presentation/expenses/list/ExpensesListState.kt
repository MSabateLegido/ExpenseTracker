package com.example.expensetracker.presentation.expenses.list

import com.example.expensetracker.domain.model.Expense
import java.time.YearMonth

data class ExpensesListState(
    val months: List<ExpensesMonth> = emptyList(),
    val expandedMonth: YearMonth? = null
)

data class ExpensesMonth(
    val month: YearMonth,
    val total: Double,
    val expenses: List<Expense>
)

