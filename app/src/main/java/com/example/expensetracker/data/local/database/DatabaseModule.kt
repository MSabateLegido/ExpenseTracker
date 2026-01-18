package com.example.expensetracker.data.local.database

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.SubcategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ExpenseTrackerDatabase =
        Room.databaseBuilder(
            context,
            ExpenseTrackerDatabase::class.java,
            "expense.db"
        ).build()

    @Provides
    fun provideExpenseDao(
        db: ExpenseTrackerDatabase
    ): ExpenseDao = db.expenseDao()

    @Provides
    fun provideCategoryDao(
        db: ExpenseTrackerDatabase
    ): CategoryDao = db.categoryDao()

    @Provides
    fun provideSubcategoryDao(
        db: ExpenseTrackerDatabase
    ): SubcategoryDao = db.subcategoryDao()
}
