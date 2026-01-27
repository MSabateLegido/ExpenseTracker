package com.example.expensetracker.presentation.expenses.list

import java.time.YearMonth


sealed interface ExpensesListEvent {

    object AddExpensesClick : ExpensesListEvent

    data class ToggleMonth(val month: YearMonth) : ExpensesListEvent
}