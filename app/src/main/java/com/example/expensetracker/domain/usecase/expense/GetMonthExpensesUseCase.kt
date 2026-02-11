package com.example.expensetracker.domain.usecase.expense

import android.util.Log
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.expense.Expense
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class GetMonthExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(yearMonth: YearMonth): Flow<List<Expense>> {
        val startOfMonth = yearMonth.atDay(1).toEpochDay()
        val endOfMonth = yearMonth.atEndOfMonth().toEpochDay()

        return expenseRepository.getMonthExpenses(startOfMonth, endOfMonth)
    }
}