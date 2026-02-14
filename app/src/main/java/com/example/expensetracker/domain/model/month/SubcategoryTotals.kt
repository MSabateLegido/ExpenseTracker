package com.example.expensetracker.domain.model.month

import com.example.expensetracker.domain.model.category.Subcategory

data class SubcategoryTotals(
    val subcategory: Subcategory,
    val total: Double
)


