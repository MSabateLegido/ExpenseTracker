package com.example.expensetracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.FavoriteDao
import com.example.expensetracker.data.local.dao.SubcategoryDao
import com.example.expensetracker.data.local.entity.CategoryEntity
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.FavoriteExpenseEntity
import com.example.expensetracker.data.local.entity.SubcategoryEntity

@Database(
    entities = [
        CategoryEntity::class,
        SubcategoryEntity::class,
        ExpenseEntity::class,
        FavoriteExpenseEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ExpenseTrackerDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun subcategoryDao(): SubcategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun favoriteDao(): FavoriteDao
}
