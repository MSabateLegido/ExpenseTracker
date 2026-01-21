package com.example.expensetracker.domain.model

import android.graphics.Color
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
