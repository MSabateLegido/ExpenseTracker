package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.dao.SubcategoryDao
import com.example.expensetracker.data.local.entity.CategoryEntity
import com.example.expensetracker.data.local.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val subcategoryDao: SubcategoryDao,
    private val expenseDao: ExpenseDao
) {

    fun getCategories(): Flow<List<CategoryEntity>> =
        categoryDao.getAll()

    fun getSubcategories(categoryId: Long): Flow<List<SubcategoryEntity>> =
        subcategoryDao.getByCategory(categoryId)

    suspend fun createCategory(
        name: String,
        color: Int,
        defaultSubcategoryName: String,
        defaultSubcategoryColor: Int
    ) {
        val categoryId = categoryDao.insert(
            CategoryEntity(
                name = name,
                color = color
            )
        )

        subcategoryDao.insert(
            SubcategoryEntity(
                name = defaultSubcategoryName,
                color = defaultSubcategoryColor,
                categoryId = categoryId
            )
        )
    }

    suspend fun addSubcategory(
        categoryId: Long,
        name: String,
        color: Int
    ) {
        subcategoryDao.insert(
            SubcategoryEntity(
                name = name,
                color = color,
                categoryId = categoryId
            )
        )
    }

    suspend fun deleteSubcategory(
        subcategoryId: Long,
        moveExpensesToSubcategoryId: Long?
    ) {
        val hasExpenses = expenseDao.hasExpenses(subcategoryId)

        if (hasExpenses) {
            require(moveExpensesToSubcategoryId != null) {
                "Subcategory has expenses, target subcategory required"
            }
            expenseDao.moveExpenses(
                fromSubcategoryId = subcategoryId,
                toSubcategoryId = moveExpensesToSubcategoryId
            )
        }

        subcategoryDao.deleteById(subcategoryId)
    }

    suspend fun deleteCategory(categoryId: Long) {
        val subcategories = subcategoryDao.countByCategory(categoryId)
        require(subcategories == 0) {
            "Cannot delete category with subcategories"
        }
        categoryDao.deleteById(categoryId)
    }
}
