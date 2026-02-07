package com.example.expensetracker.presentation.expenses.list

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import com.example.expensetracker.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
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
        topBar = { ExpensesListTopBar(stringResource(id = R.string.expenses_list_title)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ExpensesListEvent.AddExpensesClick) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    ) { innerPadding ->
        ExpensesListEffectHandler(
            effects = viewModel.effects,
            navController = navController
        )

        ExpensesListScreen(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection = LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Rtl)
                ),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}


