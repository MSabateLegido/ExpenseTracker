package com.example.expensetracker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.expensetracker.domain.model.expense.Expense

data class ExpenseWithSubcategory(
    @Embedded val expense: ExpenseEntity,

    @Relation(
        entity = SubcategoryEntity::class,
        parentColumn = "subcategoryId",
        entityColumn = "id"
    )
    val subcategory: SubcategoryWithCategory
)
fun ExpenseWithSubcategory.toUi(): Expense =
    Expense(
        id = expense.id,
        title = expense.title,
        amount = expense.amount,
        category = subcategory.toUi(),
        date = expense.date
    )


