package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.expense.DayExpenses
import java.time.YearMonth

data class MonthDetailState(
    val dayExpenses: List<DayExpenses> = emptyList(),
    val yearMonth: YearMonth = YearMonth.now()
)