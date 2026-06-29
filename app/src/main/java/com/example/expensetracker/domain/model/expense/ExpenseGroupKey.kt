package com.example.expensetracker.domain.model.expense

import com.example.expensetracker.domain.model.category.Subcategory
import java.time.LocalDate

sealed interface ExpenseGroupKey {
    data class DateGroup(val date: LocalDate) : ExpenseGroupKey
    data class SubcategoryGroup(val subcategory: Subcategory) : ExpenseGroupKey
}