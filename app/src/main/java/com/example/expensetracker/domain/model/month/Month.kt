package com.example.expensetracker.domain.model.month

import java.time.YearMonth

data class Month(
    val yearMonth: YearMonth,
    val total: Double,
    val subcategoryTotals: List<SubcategoryTotals>
)