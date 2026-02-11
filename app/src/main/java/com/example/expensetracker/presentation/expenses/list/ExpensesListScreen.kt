package com.example.expensetracker.presentation.expenses.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.month.Month
import com.example.expensetracker.utils.formatMonthYear
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
        modifier = modifier.fillMaxSize().padding(bottom = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = state.months,
            key = { it.month }
        ) { month ->
            MonthItem(
                month = month,
                onClickMonth = { onEvent(ExpensesListEvent.OnClickMonth(month.month)) }
            )
        }
    }
}

@Composable
fun MonthItem(
    month: Month,
    onClickMonth: () -> Unit
) {

    Card(
        onClick = onClickMonth,
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
                .animateContentSize()
                .padding(16.dp)
        ) {

            MonthHeader(
                month = month
            )
        }
    }
}

@Composable
fun MonthHeader(
    month: Month
) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

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




