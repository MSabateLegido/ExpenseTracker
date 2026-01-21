package com.example.expensetracker.domain.model

data class CreateCategoryWithSubcategory(
    val categoryName: String,
    val categoryColor: Int,
    val subcategoryName: String,
    val subcategoryColor: Int
)
