package com.example.expensetracker.presentation.expenses.list

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusTargetModifierNode
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
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
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = state.months,
            key = { it.month }
        ) { month ->
            MonthItem(
                month = month,
                expanded = state.expandedMonth == month.month,
                onHeaderClick = {
                    onEvent(ExpensesListEvent.ToggleMonth(month.month))
                }
            )
        }
    }
}

@Composable
fun MonthItem(
    month: ExpensesMonth,
    expanded: Boolean,
    onHeaderClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize() // ✨ animació gratis
                .padding(16.dp)
        ) {

            MonthHeader(
                month = month,
                expanded = expanded,
                onClick = onHeaderClick
            )

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .heightIn(max = 350.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = month.expenses,
                        key = { it.id }
                    ) { expense ->
                        ExpenseItem(expense)
                    }
                }
            }
        }
    }
}

@Composable
fun MonthHeader(
    month: ExpensesMonth,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = month.month.formatMonthYear(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )

            Text(
                text = formatAmount(month.total),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )
        }


        Icon(
            imageVector = if (expanded)
                Icons.Default.KeyboardArrowUp
            else
                Icons.Default.KeyboardArrowDown,
            contentDescription = null
        )
    }
}


@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                text = formatAmount(expense.amount),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = expense.date.format(DateTimeFormatter.ofPattern("dd/MM")),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun CategoryPill(
    name: String,
    color: Color
) {
    val textColor = remember(color) {
        color.contrastTextColor()
    }

    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}


fun formatAmount(amount: Double): String {
    return String.format(Locale.getDefault(), "%.2f €", amount)
}

fun Color.contrastTextColor(): Color {
    val luminance =
        (0.299 * red) +
                (0.587 * green) +
                (0.114 * blue)

    return if (luminance > 0.5f) Color.Black else Color.White
}



fun YearMonth.formatMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
    return this.atDay(1)
        .format(formatter)
        .replaceFirstChar { it.uppercase() }
}




