package com.example.expensetracker.utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun YearMonth.formatMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
    return this.atDay(1)
        .format(formatter)
        .replaceFirstChar { it.uppercase() }
}
