package com.example.expensetracker.presentation.expenses.add

sealed interface AddExpenseEffect {

    object NavigateToCategories : AddExpenseEffect

    object NavigateBack : AddExpenseEffect

}