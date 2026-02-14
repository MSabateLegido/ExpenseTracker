package com.example.expensetracker.presentation.expenses.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.usecase.category.GetSubcategoriesGroupedByCategoryUseCase
import com.example.expensetracker.domain.usecase.expense.AddExpenseUseCase
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
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getCategoriesWithSubcategories: GetSubcategoriesGroupedByCategoryUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {
    private val _effects = Channel<AddExpenseEffect>()
    val effects = _effects.receiveAsFlow()
    private val formState = MutableStateFlow(AddExpenseState())

    val state: StateFlow<AddExpenseState> =
        combine(
            formState,
            getCategoriesWithSubcategories()
        ) { form, categories ->
            form.copy(categories = categories)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddExpenseState()
        )

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            is AddExpenseEvent.NameChanged ->
                formState.update { it.copy(name = event.value) }

            is AddExpenseEvent.AmountChanged ->
                formState.update { it.copy(amount = event.value) }

            is AddExpenseEvent.DateChanged ->
                formState.update { it.copy(selectedDate = event.value) }

            is AddExpenseEvent.SubcategorySelected ->
                formState.update { it.copy(selectedSubcategory = event.subcategory) }

            is AddExpenseEvent.AddDummyExpense -> {
                formState.update {
                    it.copy(
                        name = "Dummy ${(0..500).random()}",
                        amount = 1.03f.toString(),
                        selectedSubcategory = event.categories.random().subcategories.random()
                    )
                }
                saveExpense()
            }

            AddExpenseEvent.SaveClicked -> {
                saveExpense()
                viewModelScope.launch {
                    _effects.send(AddExpenseEffect.NavigateBack)
                }
            }

            AddExpenseEvent.AddCategoryClicked ->
                viewModelScope.launch {
                    _effects.send(AddExpenseEffect.NavigateToCategories)
                }

        }
    }

    private fun saveExpense() {
        val state = formState.value

        val amount = state.amount.toDoubleOrNull() ?: return
        val subcategory = state.selectedSubcategory ?: return
        val date = state.selectedDate

        viewModelScope.launch {
            formState.update { it.copy(isSaving = true) }

            val normalizedAmount = amount
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

            addExpenseUseCase(
                Expense(
                    id = 0,
                    title = state.name,
                    amount = normalizedAmount,
                    category = subcategory,
                    date = date
                )
            )

            formState.value = AddExpenseState()
        }
    }
}
