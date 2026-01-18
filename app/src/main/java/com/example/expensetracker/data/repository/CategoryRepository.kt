package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.SubcategoryDao
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val subcategoryDao: SubcategoryDao,
    private val expenseDao: ExpenseDao
) {

}
