package com.example.expensetracker.presentation.expenses.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ExpensesListViewModel @Inject constructor(
    repository: ExpenseRepository
) : ViewModel() {

    private val _effects = Channel<ExpensesListEffect>()
    val effects = _effects.receiveAsFlow()

    val expenses: StateFlow<List<Expense>> =
        repository.getAllExpenses()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    val state: StateFlow<ExpensesListState> =
        repository.getAllExpenses()
            .map { expenses ->

                val months = expenses
                    .groupBy { expense ->
                        YearMonth.from(expense.date)
                    }
                    .map { (month, expensesOfMonth) ->

                        ExpensesMonth(
                            month = month,
                            total = calculateTotal(expensesOfMonth),
                            expenses = expensesOfMonth
                                .sortedByDescending { it.date }
                        )
                    }
                    .sortedByDescending { it.month }

                ExpensesListState(months = months)
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
            ExpensesListEvent.AddExpensesClick ->
                viewModelScope.launch {
                    _effects.send(ExpensesListEffect.NavigateToAddExpense)
                }
        }
    }

}




