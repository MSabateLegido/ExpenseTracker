package com.example.expensetracker.data.repository

import androidx.room.Transaction
import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.SubcategoryDao
import com.example.expensetracker.data.local.entity.CategoryEntity
import com.example.expensetracker.data.local.entity.SubcategoryEntity
import com.example.expensetracker.data.local.entity.toUi
import com.example.expensetracker.domain.model.Category
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
    suspend fun addCategory(category: CategoryEntity): Long = categoryDao.insert(category)

    suspend fun addSubcategory(subcategory: SubcategoryEntity): Long = subcategoryDao.insert(subcategory)


    @Transaction
    suspend fun createCategoryWithSubcategory(
        categoryName: String,
        categoryColor: Int,
        subcategoryName: String,
        subcategoryColor: Int
    ) {
        val categoryId = addCategory(
            CategoryEntity(
                name = categoryName,
                color = categoryColor
            )
        )

        addSubcategory(
            SubcategoryEntity(
                name = subcategoryName,
                color = subcategoryColor,
                categoryId = categoryId
            )
        )
    }


    fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAll()
            .map { list -> list.map { it.toUi() } }

    fun getAllSubcategories(): Flow<List<Subcategory>> =
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
