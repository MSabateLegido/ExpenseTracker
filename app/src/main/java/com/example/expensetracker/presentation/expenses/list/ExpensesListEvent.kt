package com.example.expensetracker.presentation.expenses.list

import java.time.YearMonth


sealed interface ExpensesListEvent {

    object AddExpensesClick : ExpensesListEvent
    data class DeleteExpense(val expenseId: Long) : ExpensesListEvent
    data class EditExpense(val expenseId: Long) : ExpensesListEvent
    data class DuplicateExpense(val expenseId: Long) : ExpensesListEvent

    data class ToggleMonth(val month: YearMonth) : ExpensesListEvent
}