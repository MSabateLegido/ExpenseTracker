package com.example.expensetracker.presentation.month.list

import java.time.YearMonth

sealed interface MonthListEffect {
    object NavigateToAddExpense : MonthListEffect

    data class NavigateToMonthDetail(val yearMonth: YearMonth) : MonthListEffect
}