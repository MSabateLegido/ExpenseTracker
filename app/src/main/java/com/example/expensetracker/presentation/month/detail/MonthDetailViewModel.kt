package com.example.expensetracker.presentation.month.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.category.CategoryUiModel
import com.example.expensetracker.domain.model.category.SubcategoryUiModel
import com.example.expensetracker.domain.model.expense.ExpenseRowUiModel
import com.example.expensetracker.domain.usecase.category.GetSubcategoriesGroupedByCategoryUseCase
import com.example.expensetracker.domain.usecase.expense.DeleteExpenseUseCase
import com.example.expensetracker.domain.usecase.expense.GetMonthExpensesUseCase
import com.example.expensetracker.domain.usecase.expense.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MonthDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAllExpensesUseCase: GetMonthExpensesUseCase,
    getCategoriesUseCase: GetSubcategoriesGroupedByCategoryUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel()  {

    private val _effects = Channel<MonthDetailEffect>()
    val effects = _effects.receiveAsFlow()
    private val yearMonth: YearMonth =
        YearMonth.parse(savedStateHandle["yearMonth"]!!)

    private val _uiState = MutableStateFlow(MonthDetailState(yearMonth = yearMonth))


    val state: StateFlow<MonthDetailState> =
        combine(
            _uiState,
            getAllExpensesUseCase(yearMonth = yearMonth),
            getCategoriesUseCase()
        ) { ui, expenses, categories ->

            val uiState = when (ui.grouping) {

                Grouping.ByDay -> {

                    val formatter = DateTimeFormatter.ofPattern("d MMM")

                    val grouped = expenses.groupBy { it.date }

                    val sortedDays = when (ui.sorting) {

                        Sorting.ByDefault -> {
                            when (ui.sortingOrder) {
                                SortingOrder.Ascending -> grouped.toList().sortedBy { it.first }
                                SortingOrder.Descending -> grouped.toList().sortedByDescending { it.first }
                            }
                        }

                        Sorting.ByAmount -> {
                            when (ui.sortingOrder) {
                                SortingOrder.Ascending -> grouped.toList()
                                    .sortedBy { (_, expensesOfDay) -> expensesOfDay.sumOf { it.amount } }

                                SortingOrder.Descending -> grouped.toList()
                                    .sortedByDescending { (_, expensesOfDay) -> expensesOfDay.sumOf { it.amount } }
                            }
                        }
                    }

                    val items = buildList {

                        sortedDays.forEach { (date, expensesOfDay) ->

                            val total = expensesOfDay.sumOf { it.amount }

                            add(
                                ExpenseRowUiModel.Header(
                                    title = date.format(formatter),
                                    total = total
                                )
                            )

                            val sortedExpensesOfDay = when (ui.sorting) {
                                Sorting.ByDefault -> expensesOfDay.sortedBy { it.date }
                                Sorting.ByAmount -> expensesOfDay.sortedBy { it.amount }
                            }.let {
                                when (ui.sortingOrder) {
                                    SortingOrder.Ascending -> it
                                    SortingOrder.Descending -> it.reversed()
                                }
                            }

                            sortedExpensesOfDay.forEachIndexed { index, expense ->

                                val isFirst = index == 0
                                val isLast = index == expensesOfDay.lastIndex

                                add(
                                    ExpenseRowUiModel.Item(
                                        expense = expense,
                                        isFirst = isFirst,
                                        isLast = isLast
                                    )
                                )
                            }
                        }
                    }

                    ExpenseListUiState.ByDay(items)
                }

                Grouping.ByCategory -> {

                    val categoryUiModels = categories.map { parent ->

                        val subcategoriesUi = parent.subcategories.map { subcategory ->

                            val expensesOfSubcategory = expenses
                                .filter { it.category.id == subcategory.id }

                            val sortedExpenses = when (ui.sorting) {

                                Sorting.ByDefault -> expensesOfSubcategory

                                Sorting.ByAmount -> expensesOfSubcategory
                                    .sortedBy { it.date }
                            }

                            val finalExpenses = when (ui.sortingOrder) {
                                SortingOrder.Ascending -> sortedExpenses
                                SortingOrder.Descending -> sortedExpenses.reversed()
                            }

                            SubcategoryUiModel(
                                subcategory = subcategory,
                                total = expensesOfSubcategory.sumOf { it.amount },
                                expenses = finalExpenses,
                                isExpanded = subcategory.id in ui.expandedSubcategories
                            )
                        }

                        CategoryUiModel(
                            parentCategory = parent.category,
                            total = subcategoriesUi.sumOf { it.total },
                            subcategories = subcategoriesUi
                        )
                    }

                    val sortedCategories = when (ui.sorting) {

                        Sorting.ByDefault -> categoryUiModels

                        Sorting.ByAmount -> {
                            val sorted = categoryUiModels.sortedBy { it.total }

                            when (ui.sortingOrder) {
                                SortingOrder.Ascending -> sorted
                                SortingOrder.Descending -> sorted.reversed()
                            }
                        }
                    }.map { category ->

                        val sortedSubcategories = when (ui.sorting) {

                            Sorting.ByDefault -> category.subcategories

                            Sorting.ByAmount -> {
                                val sorted = category.subcategories.sortedBy { it.total }

                                when (ui.sortingOrder) {
                                    SortingOrder.Ascending -> sorted
                                    SortingOrder.Descending -> sorted.reversed()
                                }
                            }
                        }

                        category.copy(subcategories = sortedSubcategories)
                    }

                    ExpenseListUiState.ByCategory(sortedCategories)
                }
            }

            ui.copy(
                expenses = expenses,
                categories = categories,
                uiState = uiState
            )
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                _uiState.value
            )


    fun onEvent(event: MonthDetailEvent) {
        when(event) {
            is MonthDetailEvent.OnClickSubcategory -> {
                _uiState.update { state ->

                    val id = event.subcategory.subcategory.id

                    val newExpanded = if (id in state.expandedSubcategories) {
                        state.expandedSubcategories - id
                    } else {
                        state.expandedSubcategories + id
                    }

                    state.copy(
                        expandedSubcategories = newExpanded
                    )
                }
            }

            is MonthDetailEvent.OnClickExpense -> {
                _uiState.update {
                    it.copy(selectedExpense = event.expense)
                }
            }

            is MonthDetailEvent.DeleteExpense -> {
                viewModelScope.launch {
                    deleteExpenseUseCase(event.expense)
                }
            }

            is MonthDetailEvent.UpdateExpense -> {
                viewModelScope.launch {
                    updateExpenseUseCase(event.expense)
                }
            }

            is MonthDetailEvent.UndoDelete -> {

            }

            MonthDetailEvent.OnNextMonth -> {

            }

            MonthDetailEvent.OnPreviousMonth -> {

            }

            MonthDetailEvent.DismissBottomSheet -> {
                _uiState.update {
                    it.copy(selectedExpense = null)
                }
            }

            MonthDetailEvent.AddExpensesClick -> {
                viewModelScope.launch {
                    _effects.send(MonthDetailEffect.NavigateToAddExpense)
                }
            }
        }
    }
}