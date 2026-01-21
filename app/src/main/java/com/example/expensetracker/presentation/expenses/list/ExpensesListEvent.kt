package com.example.expensetracker.presentation.expenses.list


sealed interface ExpensesListEvent {

    object AddExpensesClick : ExpensesListEvent
}