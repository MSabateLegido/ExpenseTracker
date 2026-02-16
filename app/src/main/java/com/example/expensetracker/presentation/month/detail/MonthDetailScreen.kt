package com.example.expensetracker.presentation.month.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.presentation.utils.CategoryPill
import com.example.expensetracker.utils.formatAmount
import com.example.expensetracker.utils.formatMonthYear
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


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
            contentPadding = PaddingValues(bottom = 32.dp)
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
                        expenses = dayExpenses.expenses,
                        onEvent = onEvent
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
fun ExpensesList(
    expenses: List<Expense>,
    onEvent: (MonthDetailEvent) -> Unit
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
            key(
                expense.id
            ) {
                SwipeableExpenseItem(
                    expense = expense,
                    onEdit = { onEvent(MonthDetailEvent.EditExpense(expense.id)) },
                    onDelete = { onEvent(MonthDetailEvent.DeleteExpense(expense.id)) }
                )
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableExpenseItem(
    expense: Expense,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val density = LocalDensity.current
    val maxRevealDp = 144.dp
    val maxRevealPx = with(density) { maxRevealDp.toPx() }

    val swipeableState = rememberSwipeableState(initialValue = 0)

    val anchors = mapOf(
        0f to 0,
        maxRevealPx to 1
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {

        val progress = (swipeableState.offset.value / maxRevealPx)
            .coerceIn(0f, 1f)


        Box(
            modifier = Modifier
                .matchParentSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .width(maxRevealDp * progress)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.alpha(progress)
                )
            }
        }

        ExpenseItem(
            expense = expense,
            modifier = Modifier
                .offset {
                    IntOffset(
                        swipeableState.offset.value.roundToInt(),
                        0
                    )
                }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.85f)
                    },
                    orientation = Orientation.Horizontal,
                    velocityThreshold = Int.MAX_VALUE.dp
                )
                .clickable { onEdit() }
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onDelete()
            swipeableState.snapTo(0)
        }
    }
}





@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp),
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

