package com.example.expensetracker.presentation.expenses.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.Expense
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun ExpensesListScreen(
    modifier: Modifier = Modifier,
    state: ExpensesListState,
    onEvent: (ExpensesListEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        state.months.forEach { month ->
            item {
                MonthHeader(month)
            }

            items(
                items = month.expenses,
                key = { it.id }
            ) { expense ->
                Log.i("Expense", expense.toString())
                ExpenseItem(expense)
            }
        }
    }
}

@Composable
fun MonthHeader(month: ExpensesMonth) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = month.month.formatMonthYear(),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = month.total.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = expense.category.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color(expense.category.color)
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = expense.amount.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = expense.date.format(DateTimeFormatter.ofPattern("dd/MM")),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


fun YearMonth.formatMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
    return this.atDay(1).format(formatter).uppercase()
}



