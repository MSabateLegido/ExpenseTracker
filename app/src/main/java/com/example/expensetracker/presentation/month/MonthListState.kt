package com.example.expensetracker.presentation.month

import com.example.expensetracker.domain.model.month.Month

data class MonthListState(
    val months: List<Month> = emptyList()
)

