package com.example.expensetracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracker.presentation.categories.AddCategoryRoute
import com.example.expensetracker.presentation.expenses.add.AddExpenseRoute
import com.example.expensetracker.presentation.month.ExpensesListRoute
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
                navController = navController
            )
        }

        composable(Routes.ADD_EXPENSE) {
            AddExpenseRoute(
                navController = navController
            )
        }

        composable(Routes.ADD_CATEGORY) {
            AddCategoryRoute(
                navController = navController
            )
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
