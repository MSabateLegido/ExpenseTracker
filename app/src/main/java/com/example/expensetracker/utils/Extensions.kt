package com.example.expensetracker.utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toMonthYearText(locale: Locale = Locale.getDefault()): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale)
    return format(formatter).replaceFirstChar { it.titlecase(locale) }
}
