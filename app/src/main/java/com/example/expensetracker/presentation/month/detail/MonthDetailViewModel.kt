package com.example.expensetracker.presentation.month.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.expense.DayExpenses
import com.example.expensetracker.domain.usecase.category.GetSubcategoriesGroupedByCategoryUseCase
import com.example.expensetracker.domain.usecase.expense.DeleteExpenseUseCase
import com.example.expensetracker.domain.usecase.expense.GetMonthExpensesUseCase
import com.example.expensetracker.domain.usecase.expense.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class MonthDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAllExpensesUseCase: GetMonthExpensesUseCase,
    getCategoriesUseCase: GetSubcategoriesGroupedByCategoryUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel()  {
    private val yearMonth: YearMonth =
        YearMonth.parse(savedStateHandle["yearMonth"]!!)

    private val _uiState = MutableStateFlow(MonthDetailState(yearMonth = yearMonth))


    val state: StateFlow<MonthDetailState> =
        combine(
            _uiState,
            getAllExpensesUseCase(yearMonth = yearMonth),
            getCategoriesUseCase())
        { ui, expenses, categories ->

            val grouped = expenses
                .groupBy { it.date }
                .map { (day, list) ->
                    DayExpenses(date = day, expenses = list)
                }

            ui.copy(
                dayExpenses = grouped,
                categories = categories
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            _uiState.value
        )


    fun onEvent(event: MonthDetailEvent) {
        when(event) {

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

        }
    }

    private fun closeBottomSheet() {
        _uiState.update {
            it.copy(selectedExpense = null)
        }
    }
}