package com.example.expensetracker.presentation.month.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.expensetracker.presentation.month.list.MonthListEffect
import com.example.expensetracker.utils.Routes
import kotlinx.coroutines.flow.Flow

@Composable
fun MonthDetailEffectHandler(
    effects: Flow<MonthDetailEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                MonthDetailEffect.NavigateToAddExpense -> {
                    navController.navigate(Routes.ADD_EXPENSE)
                }
            }
        }
    }
}
