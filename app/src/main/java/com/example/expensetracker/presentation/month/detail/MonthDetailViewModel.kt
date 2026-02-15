package com.example.expensetracker.presentation.month.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.usecase.expense.GetMonthExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class MonthDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAllExpensesUseCase: GetMonthExpensesUseCase,
) : ViewModel()  {
    private val yearMonth: YearMonth =
        YearMonth.parse(savedStateHandle["yearMonth"]!!)

    val state: StateFlow<MonthDetailState> =
        getAllExpensesUseCase.invoke(yearMonth = yearMonth)
            .map { expenses ->
                Log.d("MONTH_DETAIL", expenses.toString())
                MonthDetailState(
                    expenses = expenses,
                    yearMonth = yearMonth
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MonthDetailState()
            )


    fun onEvent(event: MonthDetailEvent) {
        when(event) {

            is MonthDetailEvent.DuplicateExpense -> {

            }

            is MonthDetailEvent.EditExpense -> {

            }

            is MonthDetailEvent.DeleteExpense -> {

            }

            MonthDetailEvent.OnNextMonthClicked -> {

            }

            MonthDetailEvent.OnPreviousMonthClicked -> {

            }

        }
    }
}