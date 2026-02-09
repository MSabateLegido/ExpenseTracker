package com.example.expensetracker.domain.model.month

import java.time.LocalDate
import java.time.YearMonth

data class MonthData(
    val month: LocalDate,
    val total: Double,
)

fun MonthData.toUi(): MonthData =
    MonthData(
        month = month,
        total = total
    )