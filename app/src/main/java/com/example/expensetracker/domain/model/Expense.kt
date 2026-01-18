package com.example.expensetracker.domain.model

import java.time.LocalDate

data class Expense(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: Subcategory,
    val date: LocalDate
)