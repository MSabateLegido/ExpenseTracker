package com.example.expensetracker.presentation.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.expensetracker.presentation.expenses.add.AddExpenseEffect
import com.example.expensetracker.utils.Routes
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