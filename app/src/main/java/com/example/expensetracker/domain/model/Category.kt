package com.example.expensetracker.domain.model


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.expensetracker.data.local.entity.CategoryEntity

data class Category(
    val id: Long,
    val name: String,
    val color: Color
)

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        name = name,
        color = color.toArgb()
    )
