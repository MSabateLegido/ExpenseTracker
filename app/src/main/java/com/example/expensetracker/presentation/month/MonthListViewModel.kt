package com.example.expensetracker.presentation.month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.usecase.expense.GetAllExpensesUseCase
import com.example.expensetracker.domain.usecase.month.GetMonthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthListViewModel @Inject constructor(
    getAllExpensesUseCase: GetAllExpensesUseCase,
    getMonthDataUseCase: GetMonthDataUseCase
) : ViewModel() {

    private val _effects = Channel<MonthListEffect>()
    val effects = _effects.receiveAsFlow()
    val state: StateFlow<MonthListState> =
        combine(
            getAllExpensesUseCase.invoke(),
            getMonthDataUseCase.invoke()
        ) { expenses, monthData  ->
            MonthListState(
                months = monthData
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MonthListState()
            )

    fun onEvent(event: MonthListEvent) {
        when (event) {
            is MonthListEvent.OnClickMonth -> {

            }

            is MonthListEvent.DeleteExpense -> {

            }

            is MonthListEvent.DuplicateExpense -> {

            }

            is MonthListEvent.EditExpense -> {

            }

            MonthListEvent.AddExpensesClick ->
                viewModelScope.launch {
                    _effects.send(MonthListEffect.NavigateToAddExpense)
                }
        }
    }

}




