package com.example.expensetracker.data.repository

import android.annotation.SuppressLint
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.FavoriteDao
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.FavoriteExpenseEntity
import com.example.expensetracker.data.local.entity.toUi
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
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
}
