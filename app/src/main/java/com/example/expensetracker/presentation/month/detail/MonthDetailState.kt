package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.expense.Expense
import java.time.YearMonth

data class MonthDetailState(
    val expenses: List<Expense> = emptyList(),
    val yearMonth: YearMonth = YearMonth.now()
)