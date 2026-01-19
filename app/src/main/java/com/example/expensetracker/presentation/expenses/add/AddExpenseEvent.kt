package com.example.expensetracker.presentation.expenses.add

import com.example.expensetracker.domain.model.Subcategory


sealed interface AddExpenseEvent {
    data class NameChanged(val value: String) : AddExpenseEvent
    data class AmountChanged(val value: String) : AddExpenseEvent
    data class SubcategorySelected(val subcategory: Subcategory) : AddExpenseEvent
    object SaveClicked : AddExpenseEvent
}