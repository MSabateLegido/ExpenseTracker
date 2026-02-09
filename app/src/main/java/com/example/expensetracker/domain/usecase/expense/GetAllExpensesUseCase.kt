package com.example.expensetracker.domain.usecase.expense

import com.example.expensetracker.data.repository.ExpenseRepository
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke() = expenseRepository.getAllExpenses()
}