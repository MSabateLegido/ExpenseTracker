package com.example.expensetracker.domain.usecase.month

import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.month.MonthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMonthDataUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<MonthData>> {
        return combine(
            repository.getAllMonthData(),
            repository.getMonthSubcategoryTotals()
        ) { monthTotals, subcategoryTotals ->

            monthTotals.map { month ->

                val subcategoryTotals = subcategoryTotals
                    .filter { it.yearMonth == month.month }

                MonthData(
                    yearMonth = month.month,
                    total = month.total,
                    subcategoryTotals = subcategoryTotals
                )
            }
        }
    }
}