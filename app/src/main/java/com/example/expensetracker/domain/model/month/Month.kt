package com.example.expensetracker.domain.model.month

import java.time.YearMonth

data class Month(
    val month: YearMonth,
    val total: Double
)