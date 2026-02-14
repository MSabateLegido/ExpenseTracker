package com.example.expensetracker.presentation.categories


import androidx.compose.ui.graphics.Color
import com.example.expensetracker.domain.model.category.Category

sealed interface AddCategoryEvent {
    data class ExistingCategorySelected(val category: Category) : AddCategoryEvent
    object NewCategorySelected : AddCategoryEvent

    data class CategoryNameChanged(val name: String) : AddCategoryEvent
    data class CategoryColorChanged(val color: Color) : AddCategoryEvent

    data class SubcategoryNameChanged(val name: String) : AddCategoryEvent
    data class SubcategoryColorChanged(val color: Color) : AddCategoryEvent

    object SaveClicked : AddCategoryEvent

}