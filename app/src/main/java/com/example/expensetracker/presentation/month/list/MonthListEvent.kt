package com.example.expensetracker.presentation.month.list

import java.time.YearMonth


sealed interface MonthListEvent {

    object AddExpensesClick : MonthListEvent
    data class OnClickMonth(val month: YearMonth) : MonthListEvent
}