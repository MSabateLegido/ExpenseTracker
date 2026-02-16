package com.example.expensetracker.domain.model.expense

import java.time.LocalDate

data class DayExpenses(
    val date: LocalDate,
    val expenses: List<Expense>
) {
    fun getDayTotal(): Double = expenses.sumOf { it.amount }
}