package com.example.expensetracker.presentation.month

import java.time.YearMonth


sealed interface MonthListEvent {

    object AddExpensesClick : MonthListEvent
    data class DeleteExpense(val expenseId: Long) : MonthListEvent
    data class EditExpense(val expenseId: Long) : MonthListEvent
    data class DuplicateExpense(val expenseId: Long) : MonthListEvent

    data class OnClickMonth(val month: YearMonth) : MonthListEvent
}