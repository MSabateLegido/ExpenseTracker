package com.example.expensetracker.presentation.categories

import androidx.compose.ui.graphics.Color
import com.example.expensetracker.domain.model.category.Category

data class AddCategoryState (
    val subcategoryName: String = "",
    val subcategoryColor: Color = Color.Unspecified,
    val categoryParent: Category? = null,
    val categories: List<Category> = emptyList(),

    val categoryName: String = "",
    val categoryColor: Color = Color.Unspecified,
    val isNewCategory: Boolean = false
) {
    val isSaveEnabled: Boolean
        get() {
            val subcategoryValid = subcategoryName.isNotBlank() && subcategoryColor != Color.Unspecified

            return if (isNewCategory) {
                categoryName.isNotBlank() && categoryColor != Color.Unspecified && subcategoryValid
            } else {
                categoryParent != null && subcategoryValid
            }
        }
}