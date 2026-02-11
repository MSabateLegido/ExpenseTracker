package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.presentation.month.list.MonthListEvent

sealed interface MonthDetailEvent {

    data class DeleteExpense(val expenseId: Long) : MonthDetailEvent
    data class EditExpense(val expenseId: Long) : MonthDetailEvent
    data class DuplicateExpense(val expenseId: Long) : MonthDetailEvent
}