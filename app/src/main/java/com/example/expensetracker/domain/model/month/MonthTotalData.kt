package com.example.expensetracker.domain.model.month

import java.time.YearMonth

data class MonthTotalData(
    val yearMonth: Int,
    val total: Double
)

fun MonthTotalData.toUi(): MonthTotal =
    MonthTotal(
        month = yearMonth.toYearMonth(),
        total = total
    )

fun Int.toYearMonth(): YearMonth =
    YearMonth.of(this / 100, this % 100)