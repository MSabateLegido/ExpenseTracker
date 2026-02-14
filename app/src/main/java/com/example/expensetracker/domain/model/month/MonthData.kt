package com.example.expensetracker.domain.model.month

import java.time.YearMonth

data class MonthData(
    val yearMonth: YearMonth,
    val total: Double,
    val subcategoryTotals: List<MonthSubcategoryTotals>
)