package com.example.expensetracker.domain.model.category

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.expensetracker.data.local.entity.SubcategoryEntity

data class Subcategory(
    val id: Long = 0L,
    val name: String,
    val color: Color,
    val category: Category
)

fun Subcategory.toEntity(): SubcategoryEntity =
    SubcategoryEntity(
        name = name,
        color = color.toArgb(),
        categoryId = category.id
    )
