package com.example.expensetracker.presentation.month.detail


sealed interface MonthDetailEffect {
    object NavigateToAddExpense : MonthDetailEffect
}