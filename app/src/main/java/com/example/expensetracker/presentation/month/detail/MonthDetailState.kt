package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.category.CategoryUiModel
import com.example.expensetracker.domain.model.category.CategoryWithChildren
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.expense.ExpenseRowUiModel
import java.time.YearMonth

data class MonthDetailState(
    val expenses: List<Expense> = emptyList(),
    val yearMonth: YearMonth,
    val selectedExpense: Expense? = null,
    val categories: List<CategoryWithChildren> = emptyList(),

    val grouping: Grouping = Grouping.ByCategory,
    val sorting: Sorting = Sorting.ByAmount,
    val sortingOrder: SortingOrder = SortingOrder.Ascending,

    val expandedSubcategories: Set<Long> = emptySet(),

    val uiState: ExpenseListUiState = ExpenseListUiState.Loading
)

sealed class ExpenseListUiState {
    object Loading : ExpenseListUiState()

    data class ByDay(
        val items: List<ExpenseRowUiModel>
    ) : ExpenseListUiState()

    data class ByCategory(
        val categories: List<CategoryUiModel>
    ) : ExpenseListUiState()
}

sealed class Grouping {
    object ByDay : Grouping()
    object ByCategory : Grouping()
}

sealed class Sorting {
    object ByDefault : Sorting()
    object ByAmount : Sorting()
}

sealed class SortingOrder {
    object Ascending : SortingOrder()
    object Descending : SortingOrder()
}