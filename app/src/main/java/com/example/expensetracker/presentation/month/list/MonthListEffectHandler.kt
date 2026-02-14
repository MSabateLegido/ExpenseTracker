package com.example.expensetracker.presentation.month.list

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.expensetracker.utils.Routes
import kotlinx.coroutines.flow.Flow

@Composable
fun MonthListEffectHandler(
    effects: Flow<MonthListEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                is MonthListEffect.NavigateToMonthDetail -> {
                    navController.navigate("${Routes.MONTH_DETAIL}/${effect.yearMonth}")
                }

                MonthListEffect.NavigateToAddExpense -> {
                    navController.navigate(Routes.ADD_EXPENSE)
                }
            }
        }
    }
}