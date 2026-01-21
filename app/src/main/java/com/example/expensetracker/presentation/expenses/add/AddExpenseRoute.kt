package com.example.expensetracker.presentation.expenses.add

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AddExpenseRoute(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        AddExpenseScreen(
            modifier = modifier.padding(innerPadding),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}