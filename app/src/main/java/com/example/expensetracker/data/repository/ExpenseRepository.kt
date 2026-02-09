package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.entity.toUi
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.category.toEntity
import com.example.expensetracker.domain.model.expense.toEntity
import com.example.expensetracker.domain.model.month.MonthData
import com.example.expensetracker.domain.model.month.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    suspend fun addExpense(expense: Expense) {
        expenseDao.insert(expense.toEntity())
    }

    fun getAllExpenses(): Flow<List<Expense>> =
        expenseDao
            .getAllExpenses()
            .map { list -> list.map { it.toUi() } }

    fun getAllMonthData(): Flow<List<MonthData>> =
        expenseDao
            .getAllMonthData()
            .map { list -> list.map { it.toUi() } }
}
