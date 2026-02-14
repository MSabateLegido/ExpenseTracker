package com.example.expensetracker.presentation.month.list

import com.example.expensetracker.domain.model.category.CategoryWithChildren
import com.example.expensetracker.domain.model.month.Month
import com.example.expensetracker.domain.model.month.MonthData

data class MonthListState(
    val monthTotals: List<Month> = emptyList()
)

