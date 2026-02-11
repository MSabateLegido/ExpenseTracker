package com.example.expensetracker.presentation.month

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.expensetracker.utils.Routes
import kotlinx.coroutines.flow.Flow

@Composable
fun ExpensesListEffectHandler(
    effects: Flow<MonthListEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                MonthListEffect.NavigateToAddExpense -> {
                    navController.navigate(Routes.ADD_EXPENSE)
                }
            }
        }
    }
}