package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository
import com.example.expensetracker.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> = categoryRepository.getAllCategories()
}