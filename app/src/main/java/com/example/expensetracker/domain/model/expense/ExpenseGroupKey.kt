package com.example.expensetracker.domain.model.expense

import java.time.LocalDate

sealed interface ExpenseGroupKey {
    data class Day(val date: LocalDate) : ExpenseGroupKey
    data class Subcategory(val subcategory: com.example.expensetracker.domain.model.category.Subcategory) : ExpenseGroupKey
}