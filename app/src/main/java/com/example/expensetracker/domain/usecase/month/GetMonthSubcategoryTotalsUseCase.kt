package com.example.expensetracker.domain.usecase.month

import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.month.MonthSubcategoryTotalsData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthSubcategoryTotalsUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<MonthSubcategoryTotalsData>> = repository.getMonthSubcategoryTotals()
}