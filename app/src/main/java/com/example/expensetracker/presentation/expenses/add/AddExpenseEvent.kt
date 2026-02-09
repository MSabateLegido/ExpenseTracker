package com.example.expensetracker.presentation.expenses.add

import com.example.expensetracker.domain.model.category.Subcategory
import java.time.LocalDate


sealed interface AddExpenseEvent {
    data class NameChanged(val value: String) : AddExpenseEvent
    data class AmountChanged(val value: String) : AddExpenseEvent
    data class DateChanged(val value: LocalDate) : AddExpenseEvent
    data class SubcategorySelected(val subcategory: Subcategory) : AddExpenseEvent
    object SaveClicked : AddExpenseEvent
    object AddCategoryClicked : AddExpenseEvent
    data class AddDummyExpense(val categories: List<CategoryWithChildren>): AddExpenseEvent
}