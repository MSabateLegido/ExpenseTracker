package com.example.expensetracker.domain.model.expense

sealed interface ExpenseGroupBy {

    val options: List<ExpenseOrderBy>
    val defaultOption: ExpenseOrderBy

    data object Day : ExpenseGroupBy {
        override val options = listOf(
            ExpenseOrderBy.Date(true),
            ExpenseOrderBy.Total(false)
        )
        override val defaultOption = ExpenseOrderBy.Date(true)
    }

    data object Subcategory : ExpenseGroupBy {
        override val options = listOf(
            ExpenseOrderBy.Alphabetical(true),
            ExpenseOrderBy.Total(false)
        )
        override val defaultOption = ExpenseOrderBy.Alphabetical(true)
    }
}