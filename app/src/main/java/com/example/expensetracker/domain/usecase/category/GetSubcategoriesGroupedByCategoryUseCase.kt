package com.example.expensetracker.domain.usecase.category

import com.example.expensetracker.data.repository.CategoryRepository
import com.example.expensetracker.presentation.expenses.add.CategoryWithChildren
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubcategoriesGroupedByCategoryUseCase  @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<CategoryWithChildren>> {
        return repository.getSubcategoriesGroupByCategory()
    }
}