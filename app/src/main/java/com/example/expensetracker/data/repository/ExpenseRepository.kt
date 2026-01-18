package com.example.expensetracker.data.repository

import android.annotation.SuppressLint
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.FavoriteDao
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.FavoriteExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    private val favoriteDao: FavoriteDao
) {

    fun getAllExpenses(): Flow<List<ExpenseEntity>> =
        expenseDao.getAllExpenses()

    suspend fun addExpense(
        title: String,
        amount: Double,
        subcategoryId: Long,
        date: LocalDate
    ) {
        expenseDao.insert(
            ExpenseEntity(
                title = title,
                amount = amount,
                subcategoryId = subcategoryId,
                date = date
            )
        )
    }

    suspend fun updateExpense(expense: ExpenseEntity) {
        expenseDao.update(expense)
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDao.delete(expense)
    }

    suspend fun duplicateExpense(expenseId: Long) {
        val expense = expenseDao.getById(expenseId)
            ?: error("Expense not found")

        expenseDao.insert(
            expense.copy(
                id = 0,
                date = LocalDate.now()
            )
        )
    }

    suspend fun saveAsFavorite(expenseId: Long, icon: String) {
        val expense = expenseDao.getById(expenseId)
            ?: error("Expense not found")

        favoriteDao.insert(
            FavoriteExpenseEntity(
                name = expense.title,
                amount = expense.amount,
                subcategoryId = expense.subcategoryId,
                icon = icon
            )
        )
    }
}
