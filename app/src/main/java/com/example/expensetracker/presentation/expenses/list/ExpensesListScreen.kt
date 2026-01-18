package com.example.expensetracker.presentation.expenses.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.Expense
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ExpensesListScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        state.months.forEach { month ->
            item {
                MonthHeader(month)
            }

            items(
                items = month.expenses,
                key = { it.id }
            ) { expense ->
                ExpenseRow(expense)
            }
        }
    }

}

@Composable
fun MonthHeader(
    month: ExpensesMonth,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = month.month
                .month
                .getDisplayName(TextStyle.FULL, Locale.getDefault())
                .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = month.total,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ExpenseRow(expense: Expense) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Text(
            text = expense.title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${expense.category.name} · ${expense.date}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = expense.amount,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


