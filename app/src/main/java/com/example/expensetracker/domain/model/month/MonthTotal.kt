package com.example.expensetracker.domain.model.month

import java.time.YearMonth

data class MonthTotal(
    val month: YearMonth,
    val total: Double
)