package com.example.expensetracker.domain.model.month


data class MonthSubcategoryTotalsData(
    val yearMonth: Int,
    val subcategoryId: Long,
    val total: Double
)

