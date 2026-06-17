package com.example.expensetracker.domain.model.expense

import java.time.LocalDate

sealed interface ExpenseGroupKey {
    data class Day(val date: LocalDate) : ExpenseGroupKey
    data class Category(val categoryId: Long, val subcategoryId: Long, val name: String) : ExpenseGroupKey
}