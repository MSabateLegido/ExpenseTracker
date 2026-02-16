package com.example.expensetracker.presentation.month.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.presentation.utils.CategoryPill
import com.example.expensetracker.utils.formatAmount
import com.example.expensetracker.utils.formatMonthYear
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.exp


@Composable
fun MonthDetailScreen(
    modifier: Modifier = Modifier,
    state: MonthDetailState,
    onEvent: (MonthDetailEvent) -> Unit
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        MonthTitle(
            title = state.yearMonth.formatMonthYear(),
            onNextMonth = { onEvent(MonthDetailEvent.OnNextMonthClicked) },
            onPreviousMonth = { onEvent(MonthDetailEvent.OnPreviousMonthClicked) }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            state.dayExpenses.forEach { dayExpenses ->
                item(key = "header_${dayExpenses.date}") {
                    DayHeader(
                        day = dayExpenses.date,
                        total = dayExpenses.getDayTotal()
                    )
                }

                item(key = "expenses_${dayExpenses.date}") {
                    ExpensesList(
                        expenses = dayExpenses.expenses
                    )
                }
            }
        }
    }
}

@Composable
fun MonthTitle(
    title: String,
    onNextMonth: () -> Unit,
    onPreviousMonth: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onPreviousMonth }
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(90f),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { onNextMonth }
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(90f),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun DayHeader(
    day: LocalDate,
    total: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day.format(DateTimeFormatter.ofPattern("d MMM")),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = total.formatAmount(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ExpensesList(
    expenses: List<Expense>
) {
    Card(
        modifier = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 4.dp
                ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        expenses.forEach { expense ->
            ExpenseItem(
                expense = expense
            )
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(expense.title, style = MaterialTheme.typography.bodyLarge)
            CategoryPill(
                name = expense.category.name,
                color = expense.category.color
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = expense.amount.formatAmount(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = expense.date.format(DateTimeFormatter.ofPattern("dd/MM")),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}