package com.example.expensetracker.presentation.categories.add

sealed interface AddCategoryEffect {

    object NavigateBack : AddCategoryEffect

}