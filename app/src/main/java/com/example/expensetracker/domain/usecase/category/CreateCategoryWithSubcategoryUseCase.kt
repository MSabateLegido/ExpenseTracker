package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository
import com.example.expensetracker.domain.model.category.CreateCategoryWithSubcategory
import javax.inject.Inject

class CreateCategoryWithSubcategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(command: CreateCategoryWithSubcategory) {
        repository.createCategoryWithSubcategory(
            categoryName = command.categoryName,
            categoryColor = command.categoryColor,
            subcategoryName = command.subcategoryName,
            subcategoryColor = command.subcategoryColor
        )
    }
}
