package com.example.expensetracker.utils

import androidx.compose.ui.graphics.Color
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

fun Color.contrastTextColor(): Color {
    val luminance =
        (0.299 * red) +
                (0.587 * green) +
                (0.114 * blue)

    return if (luminance > 0.5f) Color.Black else Color.White
}

fun formatAmount(amount: Double): String {
    return String.format(Locale.getDefault(), "%.2f €", amount)
}
