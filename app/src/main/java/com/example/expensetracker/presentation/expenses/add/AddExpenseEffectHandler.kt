package com.example.expensetracker.presentation.expenses.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.expensetracker.utils.Routes
import kotlinx.coroutines.flow.Flow

@Composable
fun AddExpenseEffectHandler(
    effects: Flow<AddExpenseEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                AddExpenseEffect.NavigateToCategories -> {
                    navController.navigate(Routes.ADD_CATEGORY)
                }
            }
        }
    }
}