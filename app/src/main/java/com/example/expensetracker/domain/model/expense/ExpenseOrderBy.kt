package com.example.expensetracker.domain.model.expense

sealed interface ExpenseOrderBy {

    data class Date(
        val ascending: Boolean
    ) : ExpenseOrderBy

    data class Total(
        val ascending: Boolean
    ) : ExpenseOrderBy

    data class Alphabetical(
        val ascending: Boolean
    ) : ExpenseOrderBy
}