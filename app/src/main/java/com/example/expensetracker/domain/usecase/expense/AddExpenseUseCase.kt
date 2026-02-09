package com.example.expensetracker.domain.usecase.expense

import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.expense.Expense
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {
        expenseRepository.addExpense(expense)
    }
}
