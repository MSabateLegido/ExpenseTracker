package com.example.expensetracker.presentation.expenses.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.expensetracker.utils.Routes
import kotlinx.coroutines.flow.Flow

@Composable
fun ExpensesListEffectHandler(
    effects: Flow<ExpensesListEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                ExpensesListEffect.NavigateToAddExpense -> {
                    navController.navigate(Routes.ADD_EXPENSE)
                }
            }
        }
    }
}