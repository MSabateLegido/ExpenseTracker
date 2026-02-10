package com.example.expensetracker.domain.model.month

import java.time.LocalDate
import java.time.YearMonth

data class MonthData(
    val year: Int,
    val month: Int,
    val total: Double,
)

fun MonthData.toUi(): Month =
    Month(
        month = YearMonth.of(year, month),
        total = total
    )