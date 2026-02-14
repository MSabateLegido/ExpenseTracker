package com.example.expensetracker.domain.model.month

import com.example.expensetracker.domain.model.category.Subcategory
import java.time.YearMonth

data class MonthSubcategoryTotals(
    val yearMonth: YearMonth,
    val subcategory: Int,
    val total: Double
)


