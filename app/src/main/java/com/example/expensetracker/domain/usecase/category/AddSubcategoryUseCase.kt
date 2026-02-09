package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository
import com.example.expensetracker.domain.model.category.Subcategory
import com.example.expensetracker.domain.model.category.toEntity
import javax.inject.Inject

class AddSubcategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(subcategory: Subcategory) =
        categoryRepository.addSubcategory(subcategory.toEntity())
}
