package com.example.expensetracker.presentation.month.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.presentation.utils.ExpenseTrackerTopbar
import com.example.expensetracker.utils.formatMonthYear
import java.time.YearMonth

@Composable
fun MonthDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: MonthDetailViewModel = hiltViewModel(),
    navController: NavController,
    yearMonth: YearMonth
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ExpenseTrackerTopbar(
                title = stringResource(R.string.month_detail_title),
                onBack = { navController.navigateUp() }
            )
        }
    ) {  innerPadding ->

        MonthDetailScreen(
            modifier = modifier.padding(innerPadding),
            state = state,
            onEvent = viewModel::onEvent
        )
    }

}