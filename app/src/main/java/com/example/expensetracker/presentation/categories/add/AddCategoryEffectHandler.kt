package com.example.expensetracker.presentation.categories.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

@Composable
fun AddCategoryEffectHandler (
    effects: Flow<AddCategoryEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                AddCategoryEffect.NavigateBack -> {
                    navController.navigateUp()
                }
            }
        }
    }
}