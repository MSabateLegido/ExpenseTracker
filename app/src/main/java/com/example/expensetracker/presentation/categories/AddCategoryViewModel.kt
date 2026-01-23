package com.example.expensetracker.presentation.categories

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.CreateCategoryWithSubcategory
import com.example.expensetracker.domain.model.Subcategory
import com.example.expensetracker.domain.usecase.category.AddSubcategoryUseCase
import com.example.expensetracker.domain.usecase.category.CreateCategoryWithSubcategoryUseCase
import com.example.expensetracker.domain.usecase.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val createCategoryWithSubcategoryUseCase: CreateCategoryWithSubcategoryUseCase,
    private val addSubcategoryUseCase: AddSubcategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val formState = MutableStateFlow(AddCategoryState())

    val state: StateFlow<AddCategoryState> =
        combine(
            formState,
            getCategoriesUseCase()
        ) { form, categories ->
            form.copy(categories = categories)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddCategoryState()
        )


    fun onEvent(event: AddCategoryEvent) {
        when (event) {
            is AddCategoryEvent.CategoryColorChanged ->
                formState.update { it.copy(categoryColor = event.color) }

            is AddCategoryEvent.CategoryNameChanged ->
                formState.update { it.copy(categoryName = event.name) }

            is AddCategoryEvent.SubcategoryNameChanged ->
                formState.update { it.copy(subcategoryName = event.name) }

            is AddCategoryEvent.SubcategoryColorChanged ->
                formState.update { it.copy(subcategoryColor = event.color) }

            is AddCategoryEvent.ExistingCategorySelected ->
                formState.update {
                    it.copy(
                        categoryParent = event.category,
                        isNewCategory = false
                    )
                }

            AddCategoryEvent.NewCategorySelected ->
                formState.update {
                    it.copy(
                        categoryParent = null,
                        isNewCategory = true
                    )
                }

            AddCategoryEvent.SaveClicked ->
                saveCategory()
        }
    }

    private fun saveCategory() {
        val s = state.value

        if (s.subcategoryName.isBlank()) return
        if (s.isNewCategory && s.categoryName.isBlank()) return
        if (!s.isNewCategory && s.categoryParent == null) return

        viewModelScope.launch {

            if (s.isNewCategory) {
                createCategoryWithSubcategoryUseCase(
                    CreateCategoryWithSubcategory(
                        categoryName = s.categoryName,
                        categoryColor = s.categoryColor.toArgb(),
                        subcategoryName = s.subcategoryName,
                        subcategoryColor = s.subcategoryColor.toArgb()
                    )
                )
            } else {
                addSubcategoryUseCase(
                    Subcategory(
                        name = s.subcategoryName,
                        color = s.subcategoryColor.toArgb(),
                        category = s.categoryParent?: return@launch
                    )
                )
            }
        }
    }

}