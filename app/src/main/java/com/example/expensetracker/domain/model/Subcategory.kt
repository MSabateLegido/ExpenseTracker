package com.example.expensetracker.domain.model

import com.example.expensetracker.data.local.entity.SubcategoryEntity

data class Subcategory(
    val id: Long = 0L,
    val name: String,
    val color: Int,
    val category: Category
)

fun Subcategory.toEntity(): SubcategoryEntity =
    SubcategoryEntity(
        name = name,
        color = color,
        categoryId = category.id
    )
