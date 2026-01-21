package com.example.expensetracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracker.presentation.expenses.add.AddExpenseRoute
import com.example.expensetracker.presentation.expenses.list.ExpensesListRoute
import com.example.expensetracker.utils.Routes

@Composable
fun ExpenseTrackerNavHost(
    navController: NavHostController,
    startDestination: String = Routes.DASHBOARD
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.DASHBOARD) {
            ExpensesListRoute(
                onAddExpense = {
                    navController.navigate(Routes.ADD_EXPENSE)
                }
            )
        }

        composable(Routes.ADD_EXPENSE) {
            AddExpenseRoute()
        }

        composable(Routes.ADD_CATEGORY) {
            /*AddCategoryScreen(
                onBack = { navController.popBackStack() }
            )*/
        }

        composable(Routes.CATEGORIES) {
            /*CategoriesScreen(
                onBack = { navController.popBackStack() }
            )*/
        }

        composable(Routes.STATS) {
            /*StatsScreen(
                onBack = { navController.popBackStack() }
            )*/
        }
    }
}
