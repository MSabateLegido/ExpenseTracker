package com.example.expensetracker.presentation.month.list

import android.util.Log
import androidx.compose.runtime.key
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.category.Subcategory
import com.example.expensetracker.domain.model.month.Month
import com.example.expensetracker.domain.model.month.SubcategoryTotals
import com.example.expensetracker.domain.usecase.category.GetSubcategoriesGroupedByCategoryUseCase
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
    getMonthDataUseCase: GetMonthDataUseCase,
    getSubcategoriesUseCase: GetSubcategoriesGroupedByCategoryUseCase
) : ViewModel() {

    private val _effects = Channel<MonthListEffect>()
    val effects = _effects.receiveAsFlow()
    val state: StateFlow<MonthListState> =
        combine(
            getMonthDataUseCase.invoke(),
            getSubcategoriesUseCase.invoke()
        ) { monthData, categories ->
            val subcategoryMap: Map<Long, Subcategory> =
                categories
                    .flatMap { it.subcategories }
                    .associateBy { it.id }
            val months: List<Month> = monthData.map { month ->
                val mappedSubcategories = month.subcategoryTotals.mapNotNull { subTotal ->
                    val subcategory = subcategoryMap[subTotal.subcategoryId]
                    subcategory?.let {
                        SubcategoryTotals(
                            subcategory = it,
                            total = subTotal.total
                        )
                    }
                }


                Month(
                    yearMonth = month.yearMonth,
                    total = month.total,
                    subcategoryTotals = mappedSubcategories
                )
            }
            months.forEach { month ->
                month.subcategoryTotals.forEach { subcategoryTotals ->
                    Log.d("MONTHLIST", "Month: ${month.yearMonth}, Subcategory: ${subcategoryTotals.subcategory}, Total: ${subcategoryTotals.total}")
                }
            }
            MonthListState(
                monthTotals = months
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MonthListState()
        )

    fun onEvent(event: MonthListEvent) {
        when (event) {
            is MonthListEvent.OnClickMonth -> {
                viewModelScope.launch {
                    _effects.send(MonthListEffect.NavigateToMonthDetail(event.month))
                }
            }

            MonthListEvent.AddExpensesClick ->
                viewModelScope.launch {
                    _effects.send(MonthListEffect.NavigateToAddExpense)
                }
        }
    }

}




