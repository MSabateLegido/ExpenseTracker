package com.example.expensetracker.domain.model.month

import java.time.LocalDate
import java.time.YearMonth

data class MonthData(
    val yearMonth: Int,
    val total: Double
)

fun MonthData.toUi(): Month =
    Month(
        month = yearMonth.toYearMonth(),
        total = total
    )

fun Int.toYearMonth(): YearMonth =
    YearMonth.of(this / 100, this % 100)