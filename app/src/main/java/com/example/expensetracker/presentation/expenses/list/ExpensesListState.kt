package com.example.expensetracker.presentation.expenses.list

import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.month.Month
import com.example.expensetracker.domain.model.month.MonthData
import java.time.LocalDate
import java.time.YearMonth

data class ExpensesListState(
    val months: List<Month> = emptyList()
)

