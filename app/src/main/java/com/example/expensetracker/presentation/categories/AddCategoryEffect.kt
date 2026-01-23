package com.example.expensetracker.presentation.categories

sealed interface AddCategoryEffect {

    object NavigateBack : AddCategoryEffect

}