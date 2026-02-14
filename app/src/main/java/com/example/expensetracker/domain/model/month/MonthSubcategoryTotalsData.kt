package com.example.expensetracker.domain.model.month


data class MonthSubcategoryTotalsData(
    val yearMonth: Int,
    val subcategoryId: Int,
    val total: Double
)

fun MonthSubcategoryTotalsData.toUi(): MonthSubcategoryTotals =
    MonthSubcategoryTotals(
        yearMonth = yearMonth.toYearMonth(),
        subcategory = subcategoryId,
        total = total
    )

