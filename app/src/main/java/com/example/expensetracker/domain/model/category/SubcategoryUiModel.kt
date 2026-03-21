package com.example.expensetracker.domain.model.category

import com.example.expensetracker.domain.model.expense.Expense

data class SubcategoryUiModel(
    val subcategory: Subcategory,
    val total: Double,
    val expenses: List<Expense>,
    val isExpanded: Boolean
)