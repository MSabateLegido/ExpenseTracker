package com.example.expensetracker

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.presentation.categories.AddCategoryRoute
import com.example.expensetracker.presentation.expenses.add.AddExpenseRoute
import com.example.expensetracker.presentation.month.detail.MonthDetailRoute
import com.example.expensetracker.presentation.month.list.MonthListRoute
import com.example.expensetracker.utils.Routes
import java.time.YearMonth

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
            MonthListRoute(
                navController = navController
            )
        }

        composable(
            route = "${Routes.MONTH_DETAIL}/{yearMonth}",
            arguments = listOf(
                navArgument("yearMonth") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val yearMonthString = backStackEntry.arguments?.getString("yearMonth")
            val yearMonth = YearMonth.parse(yearMonthString!!)
            MonthDetailRoute(
                navController = navController,
                yearMonth = yearMonth
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
