package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.presentation.expenses.list.ExpensesListScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()

                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.DASHBOARD,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.DASHBOARD) {
                            ExpensesListScreen(
                                onAddExpense = {
                                    navController.navigate(Routes.ADD_EXPENSE)
                                }
                            )
                        }

                        composable(Routes.ADD_EXPENSE) {
                            /*AddExpenseScreen(
                                onDone = {
                                    navController.popBackStack()
                                },
                                onAddCategory = {
                                    navController.navigate(Routes.ADD_CATEGORY)
                                }
                            )*/
                        }

                        composable(Routes.ADD_CATEGORY) {
                            /*AddCategoryScreen(
                                onDone = {
                                    navController.popBackStack()
                                }
                            )*/
                        }

                        composable(Routes.CATEGORIES) {
                            //CategoriesScreen()
                        }

                        composable(Routes.STATS) {
                            //StatsScreen()
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ExpensesListScreen(
    modifier: Modifier = Modifier,
    onAddExpense: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpense) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    ) { innerPadding ->
        ExpensesListScreen(
            modifier = modifier.padding(innerPadding)
        )
    }
}

