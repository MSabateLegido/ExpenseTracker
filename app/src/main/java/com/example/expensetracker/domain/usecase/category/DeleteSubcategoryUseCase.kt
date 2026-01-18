package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository

class DeleteSubcategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(
        subcategoryId: Long,
        moveExpensesToSubcategoryId: Long?
    ): Result<Unit> {
        return runCatching {
            categoryRepository.deleteSubcategory(
                subcategoryId = subcategoryId,
                moveExpensesToSubcategoryId = moveExpensesToSubcategoryId
            )
        }
    }
}