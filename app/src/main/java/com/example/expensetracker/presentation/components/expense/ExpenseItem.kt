package com.example.expensetracker.presentation.components.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.utils.formatAmount

@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier,
    secondaryContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(expense.title, style = MaterialTheme.typography.bodyLarge)

            secondaryContent?.invoke()
        }

        Text(
            text = expense.amount.formatAmount(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}