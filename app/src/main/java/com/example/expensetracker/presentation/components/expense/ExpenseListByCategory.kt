package com.example.expensetracker.presentation.components.expense

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.category.CategoryUiModel
import com.example.expensetracker.domain.model.category.SubcategoryUiModel
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.presentation.components.category.CategoryChip
import com.example.expensetracker.presentation.components.category.CategoryChipSize
import com.example.expensetracker.utils.formatAmount


@Composable
fun ExpenseListByCategory(
    categories: List<CategoryUiModel>,
    onClickExpense: (Expense) -> Unit,
    onClickSubcategory: (SubcategoryUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(categories) { category ->
            CategorySection(
                category = category,
                onClickExpense = onClickExpense,
                onClickSubcategory = onClickSubcategory
            )
        }
    }
}

@Composable
fun CategorySection(
    category: CategoryUiModel,
    onClickExpense: (Expense) -> Unit,
    onClickSubcategory: (SubcategoryUiModel) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryChip(
                name = category.parentCategory.name,
                color = category.parentCategory.color,
                size = CategoryChipSize.Large
            )

            Text(
                text = category.total.formatAmount(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column {
                category.subcategories.forEachIndexed { index, subcategory ->

                    SubcategoryItem(
                        subcategory = subcategory,
                        onClickExpense = onClickExpense,
                        onClickSubcategory = onClickSubcategory
                    )

                    if (index != category.subcategories.lastIndex) {
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubcategoryItem(
    subcategory: SubcategoryUiModel,
    onClickExpense: (Expense) -> Unit,
    onClickSubcategory: (SubcategoryUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickSubcategory(subcategory) }
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            CategoryChip(
                name = subcategory.subcategory.name,
                color = subcategory.subcategory.color,
                size = CategoryChipSize.Medium
            )

            Text(
                text = subcategory.total.formatAmount(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (subcategory.isExpanded) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column {
                    subcategory.expenses.forEachIndexed { index, expense ->

                        ExpenseItemRow(
                            expense = expense,
                            onClick = { onClickExpense(expense) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItemRow(
    expense: Expense,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = expense.title,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = expense.amount.formatAmount(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}