package com.example.expensetracker.presentation.month.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import java.time.YearMonth

@Composable
fun MonthDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: MonthDetailViewModel = hiltViewModel(),
    navController: NavController,
    yearMonth: YearMonth
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

}