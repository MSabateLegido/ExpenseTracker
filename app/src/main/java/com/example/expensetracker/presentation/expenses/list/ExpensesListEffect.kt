package com.example.expensetracker.presentation.expenses.list

sealed interface ExpensesListEffect {
    object NavigateToAddExpense : ExpensesListEffect
}