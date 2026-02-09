package com.example.expensetracker.presentation.expenses.list

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.usecase.expense.GetAllExpensesUseCase
import com.example.expensetracker.domain.usecase.month.GetMonthDataUseCase
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
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ExpensesListViewModel @Inject constructor(
    getAllExpensesUseCase: GetAllExpensesUseCase,
    getMonthDataUseCase: GetMonthDataUseCase
) : ViewModel() {

    private val _effects = Channel<ExpensesListEffect>()
    val effects = _effects.receiveAsFlow()
    private val expandedMonth = MutableStateFlow<LocalDate?>(LocalDate.now())
    val state: StateFlow<ExpensesListState> =
        combine(
            getAllExpensesUseCase.invoke(),
            getMonthDataUseCase.invoke(),
            expandedMonth
        ) { expenses, monthData, expandedMonth  ->
            ExpensesListState(
                months = monthData,
                expandedMonth = expandedMonth
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ExpensesListState()
            )


    fun calculateTotal(expenses: List<Expense>): Double =
        expenses.sumOf { it.amount }

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.ToggleMonth -> {
                expandedMonth.update { current ->
                    if (current == event.month) null else event.month
                }
            }

            is ExpensesListEvent.DeleteExpense -> {
                TODO()
            }

            is ExpensesListEvent.DuplicateExpense -> {
                TODO()
            }

            is ExpensesListEvent.EditExpense -> {
                TODO()
            }

            ExpensesListEvent.AddExpensesClick ->
                viewModelScope.launch {
                    _effects.send(ExpensesListEffect.NavigateToAddExpense)
                }
        }
    }

}




