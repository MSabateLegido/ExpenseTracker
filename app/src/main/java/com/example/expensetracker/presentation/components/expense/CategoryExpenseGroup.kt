package com.example.expensetracker.presentation.components.expense

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.expensetracker.domain.model.category.Subcategory
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.presentation.components.category.SubcategoryHeader
import com.example.expensetracker.presentation.month.detail.MonthDetailEvent
import java.time.format.DateTimeFormatter

@Composable
fun CategoryExpenseGroup(
    subcategory: Subcategory,
    total: Double,
    expenses: List<Expense>,
    onEvent: (MonthDetailEvent) -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val formatter = remember {
        DateTimeFormatter.ofPattern("d MMM")
    }

    Column {
        SubcategoryHeader(
            subcategory = subcategory,
            total = total,
            onClick = {
                expanded = !expanded
            }
        )

        AnimatedVisibility(expanded) {
            Column {
                expenses.forEachIndexed { index, expense ->
                    ExpenseCardItem(
                        expense = expense,
                        isFirst = index == 0,
                        isLast = index == expenses.lastIndex,
                        onClickExpense = {
                            onEvent(MonthDetailEvent.OnClickExpense(expense))
                        },
                        secondaryContent = {
                            Text(
                                text = expense.date.format(formatter),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }
        }
    }
}