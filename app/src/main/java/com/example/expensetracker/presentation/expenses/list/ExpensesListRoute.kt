package com.example.expensetracker.presentation.expenses.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun ExpensesListRoute(
    modifier: Modifier = Modifier,
    viewModel: ExpensesListViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(ExpensesListEvent.AddExpensesClick) }) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    ) { innerPadding ->
        ExpensesListEffectHandler(
            effects = viewModel.effects,
            navController = navController
        )

        ExpensesListScreen(
            modifier = modifier.padding(innerPadding),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}
