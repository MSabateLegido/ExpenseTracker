package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.SubcategoryDao
import com.example.expensetracker.data.local.entity.toUi
import com.example.expensetracker.domain.model.Subcategory
import com.example.expensetracker.presentation.expenses.add.CategoryWithChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val subcategoryDao: SubcategoryDao,
    private val expenseDao: ExpenseDao
) {

    fun getAllSubategories(): Flow<List<Subcategory>> =
        subcategoryDao.getAllSubcategories()
            .map { list -> list.map { it.toUi() } }

    fun getSubcategoriesGroupByCategory(): Flow<List<CategoryWithChildren>> =
        subcategoryDao.getAllSubcategories()
            .map { list ->
                list
                    .groupBy { it.category.toUi() }
                    .map { (category, items) ->
                        CategoryWithChildren(
                            category = category,
                            subcategories = items.map { it.toUi() }
                        )
                    }
            }

}
