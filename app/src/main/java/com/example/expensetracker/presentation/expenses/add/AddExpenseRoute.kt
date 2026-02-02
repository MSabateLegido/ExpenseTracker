package com.example.expensetracker.presentation.expenses.add

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.presentation.utils.ExpenseTrackerTopbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseRoute(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ExpenseTrackerTopbar(
                onClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        AddExpenseEffectHandler(
            effects = viewModel.effects,
            navController = navController
        )

        AddExpenseScreen(
            modifier = modifier.padding(innerPadding),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}