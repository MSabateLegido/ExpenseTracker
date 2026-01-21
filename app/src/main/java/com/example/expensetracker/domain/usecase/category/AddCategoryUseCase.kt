package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.Category
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.toEntity
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(category: Category) {
        categoryRepository.addCategory(category.toEntity())
    }
}