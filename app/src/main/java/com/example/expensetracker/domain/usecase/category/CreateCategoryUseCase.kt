package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(
        name: String,
        color: Int,
        defaultSubcategoryName: String,
        defaultSubcategoryColor: Int
    ) {
        categoryRepository.createCategory(
            name,
            color,
            defaultSubcategoryName,
            defaultSubcategoryColor)
    }
}