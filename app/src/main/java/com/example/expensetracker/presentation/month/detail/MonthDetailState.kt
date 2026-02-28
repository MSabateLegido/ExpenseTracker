package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.category.CategoryWithChildren
import com.example.expensetracker.domain.model.expense.DayExpenses
import com.example.expensetracker.domain.model.expense.Expense
import java.time.YearMonth

data class MonthDetailState(
    val dayExpenses: List<DayExpenses> = emptyList(),
    val yearMonth: YearMonth = YearMonth.now(),
    val selectedExpense: Expense? = null,
    val categories: List<CategoryWithChildren> = emptyList()
)