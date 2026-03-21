package com.example.expensetracker.domain.model.category


data class CategoryUiModel(
    val parentCategory: Category,
    val total: Double,
    val subcategories: List<SubcategoryUiModel>
)