package com.example.expensetracker.presentation.expenses.add

import com.example.expensetracker.domain.model.category.Category
import com.example.expensetracker.domain.model.category.Subcategory
import java.time.LocalDate


data class AddExpenseState(
    val name: String = "",
    val amount: String = "",
    val selectedSubcategory: Subcategory? = null,
    val categories: List<CategoryWithChildren> = emptyList(),
    val isSaving: Boolean = false,
    val error: String? = null,
    val selectedDate: LocalDate = LocalDate.now()
) {
    val isSaveEnabled: Boolean = name.isNotBlank()
            && amount.isNotBlank()
            && selectedSubcategory != null
            && !isSaving
}

data class CategoryWithChildren (
    val category: Category,
    val subcategories: List<Subcategory>
)