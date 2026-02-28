package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.expense.Expense

sealed interface MonthDetailEvent {
    data class OnClickExpense(val expense: Expense) : MonthDetailEvent
    data class UpdateExpense(val expense: Expense) : MonthDetailEvent
    data object DismissBottomSheet : MonthDetailEvent
    data class DeleteExpense(val expense: Expense) : MonthDetailEvent
    data class UndoDelete(val expense: Expense) : MonthDetailEvent
    object OnPreviousMonth : MonthDetailEvent
    object OnNextMonth : MonthDetailEvent
}