package com.example.expensetracker.domain.model.month

import com.example.expensetracker.utils.toYearMonth

data class MonthTotalData(
    val yearMonth: Int,
    val total: Double
)

fun MonthTotalData.toUi(): MonthTotal =
    MonthTotal(
        month = yearMonth.toYearMonth(),
        total = total
    )
