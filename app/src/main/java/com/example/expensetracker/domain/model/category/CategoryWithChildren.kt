package com.example.expensetracker.domain.model.category


data class CategoryWithChildren (
    val category: Category,
    val subcategories: List<Subcategory>
)