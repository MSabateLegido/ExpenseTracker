package com.example.expensetracker.presentation.expenses.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ExpensesListViewModel @Inject constructor(
    getAllExpensesUseCase: GetAllExpensesUseCase,
    getMonthDataUseCase: GetMonthDataUseCase
) : ViewModel() {

    private val _effects = Channel<ExpensesListEffect>()
    val effects = _effects.receiveAsFlow()
    val state: StateFlow<ExpensesListState> =
        combine(
            getAllExpensesUseCase.invoke(),
            getMonthDataUseCase.invoke()
        ) { expenses, monthData  ->
            ExpensesListState(
                months = monthData
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ExpensesListState()
            )

    fun onEvent(event: ExpensesListEvent) {
        when (event) {
            is ExpensesListEvent.OnClickMonth -> {

            }

            is ExpensesListEvent.DeleteExpense -> {

            }

            is ExpensesListEvent.DuplicateExpense -> {

            }

            is ExpensesListEvent.EditExpense -> {

            }

            ExpensesListEvent.AddExpensesClick ->
                viewModelScope.launch {
                    _effects.send(ExpensesListEffect.NavigateToAddExpense)
                }
        }
    }

}




