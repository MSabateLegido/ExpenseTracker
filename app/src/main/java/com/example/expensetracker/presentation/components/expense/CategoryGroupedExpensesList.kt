package com.example.expensetracker.presentation.components.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.ExpenseGroup
import com.example.expensetracker.domain.model.expense.ExpenseGroupKey
import com.example.expensetracker.presentation.month.detail.MonthDetailEvent

@Composable
fun CategoryGroupedExpensesList(
    expenseGroups: List<ExpenseGroup>,
    onEvent: (MonthDetailEvent) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(
            items = expenseGroups,
            key = { group ->
                (group.key as ExpenseGroupKey.SubcategoryGroup).subcategory.id
            }
        ) { group ->

            val key = group.key as ExpenseGroupKey.SubcategoryGroup

            CategoryExpenseGroup(
                subcategory = key.subcategory,
                total = group.total,
                expenses = group.expenses,
                onEvent = onEvent
            )
        }
    }
}