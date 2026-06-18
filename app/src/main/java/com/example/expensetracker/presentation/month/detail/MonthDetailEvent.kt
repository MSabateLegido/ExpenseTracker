package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.expense.ExpenseGroupBy
import com.example.expensetracker.domain.model.expense.ExpenseOrderBy
import com.example.expensetracker.presentation.month.list.MonthListEvent

sealed interface MonthDetailEvent {
    data class OnClickExpense(val expense: Expense) : MonthDetailEvent
    data class UpdateExpense(val expense: Expense) : MonthDetailEvent
    data object DismissBottomSheet : MonthDetailEvent
    data class DeleteExpense(val expense: Expense) : MonthDetailEvent
    data class UndoDelete(val expense: Expense) : MonthDetailEvent
    data class ChangeGroupBy(val by: ExpenseGroupBy) : MonthDetailEvent
    data class ChangeOrderBy(val by: ExpenseOrderBy) : MonthDetailEvent
    object OnPreviousMonth : MonthDetailEvent
    object OnNextMonth : MonthDetailEvent
    object AddExpensesClick : MonthDetailEvent
}