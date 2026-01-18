package com.example.expensetracker.domain.model

data class Subcategory(
    val id: Long = 0L,
    val name: String,
    val color: Int,
    val category: Category
)
