package com.example.expensetracker.presentation.components.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.expense.ExpenseRowUiModel
import com.example.expensetracker.presentation.components.category.CategoryPill
import com.example.expensetracker.utils.formatAmount

@Composable
fun ExpenseListByDay (
    items: List<ExpenseRowUiModel>,
    onClickExpense: (Expense) -> Unit,
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {

        items(items) { item ->

            when (item) {
                is ExpenseRowUiModel.Header -> {
                    DayHeader(
                        title = item.title,
                        total = item.total
                    )
                }

                is ExpenseRowUiModel.Item -> {
                    ExpenseCardItem(
                        expense = item.expense,
                        isFirst = item.isFirst,
                        isLast = item.isLast,
                        onClickExpense = { onClickExpense(item.expense) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}

@Composable
fun DayHeader(
    title: String,
    total: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
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
fun ExpenseCardItem(
    expense: Expense,
    isFirst: Boolean,
    isLast: Boolean,
    onClickExpense: () -> Unit,
    modifier: Modifier = Modifier
) {

    val shape = when {
        isFirst && isLast -> RoundedCornerShape(8.dp)
        isFirst -> RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
        isLast -> RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        )
        else -> RoundedCornerShape(0.dp)
    }

    Card(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickExpense() },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        ExpenseItem(
            expense = expense,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(expense.title, style = MaterialTheme.typography.bodyLarge)
            CategoryPill(
                name = expense.category.name,
                color = expense.category.color
            )
        }

        Text(
            text = expense.amount.formatAmount(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}