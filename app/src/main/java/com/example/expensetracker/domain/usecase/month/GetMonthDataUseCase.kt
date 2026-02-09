package com.example.expensetracker.domain.usecase.month

import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.month.MonthData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthDataUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke() = repository.getAllMonthData()
}