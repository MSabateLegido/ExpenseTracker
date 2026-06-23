package com.example.expensetracker.presentation.components.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.presentation.components.category.CategoryPill

@Composable
fun ExpenseCardItem(
    expense: Expense,
    isFirst: Boolean,
    isLast: Boolean,
    onClickExpense: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryContent: @Composable (() -> Unit)? = null
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
            modifier = modifier.fillMaxWidth(),
            secondaryContent = secondaryContent
        )
    }
}