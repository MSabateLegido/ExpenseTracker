package com.example.expensetracker.domain.model.expense

import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.domain.model.category.Subcategory
import java.time.LocalDate

data class Expense(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: Subcategory,
    val date: LocalDate
)

fun Expense.toEntity(): ExpenseEntity =
    ExpenseEntity(
        title = title,
        amount = amount,
        subcategoryId = category.id,
        date = date
    )