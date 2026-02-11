package com.example.expensetracker.presentation.month

sealed interface MonthListEffect {
    object NavigateToAddExpense : MonthListEffect
}