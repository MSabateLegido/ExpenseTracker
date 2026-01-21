package com.example.expensetracker.presentation.categories

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensetracker.presentation.expenses.add.AddExpenseScreen
import com.example.expensetracker.presentation.expenses.add.AddExpenseViewModel

@Composable
fun AddCategoryRoute(
    modifier: Modifier = Modifier,
    viewModel: AddCategoryViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddCategoryScreen(
        state = state,
        onEvent = viewModel::onEvent
    )

}