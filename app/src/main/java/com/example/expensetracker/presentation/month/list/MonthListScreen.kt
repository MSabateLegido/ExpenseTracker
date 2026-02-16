package com.example.expensetracker.presentation.month.list

import android.graphics.Canvas
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.month.Month
import com.example.expensetracker.domain.model.month.MonthData
import com.example.expensetracker.domain.model.month.MonthTotal
import com.example.expensetracker.domain.model.month.SubcategoryTotals
import com.example.expensetracker.presentation.utils.CategoryPill
import com.example.expensetracker.utils.formatAmount
import com.example.expensetracker.utils.formatMonthYear
import java.time.YearMonth


@Composable
fun MonthListScreen(
    modifier: Modifier = Modifier,
    state: MonthListState,
    onEvent: (MonthListEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = state.monthTotals,
            key = { it.yearMonth }
        ) { month ->
            MonthItem(
                monthData = month,
                onClickMonth = { onEvent(MonthListEvent.OnClickMonth(month.yearMonth)) }
            )
        }
    }
}

@Composable
fun MonthItem(
    monthData: Month,
    onClickMonth: () -> Unit
) {
    Card(
        onClick = onClickMonth,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                month = monthData.yearMonth,
                monthTotal = monthData.total
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
                ExpensePieChart(
                    total = monthData.total,
                    subcategoryTotals = monthData.subcategoryTotals,
                    modifier = Modifier.size(75.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                MonthCategoryGrid(
                    subcategoryTotals = monthData.subcategoryTotals
                )
            }
        }
    }
}

@Composable
fun MonthHeader(
    month: YearMonth,
    monthTotal: Double
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
            text = month.formatMonthYear(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start
        )

        Text(
            text = monthTotal.formatAmount(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )

    }
}

@Composable
fun ExpensePieChart(
    total: Double,
    subcategoryTotals: List<SubcategoryTotals>,
    modifier: Modifier = Modifier
) {
    if (total <= 0.0) return

    Canvas(
        modifier = modifier
            .aspectRatio(1f)
    ) {

        var startAngle = -90f // Comença a les 12 en punt

        subcategoryTotals.forEach { item ->

            val sweepAngle = ((item.total / total) * 360f).toFloat()

            drawArc(
                color = item.subcategory.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )

            startAngle += sweepAngle
        }
    }
}

@Composable
fun MonthCategoryGrid(
    subcategoryTotals: List<SubcategoryTotals>,
    modifier: Modifier = Modifier
) {

    val sortedItems = remember(subcategoryTotals) {
        subcategoryTotals
            .filter { it.total > 0.0 }
            .sortedByDescending { it.total }
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxLines = 2
    ) {

        sortedItems.forEach { item ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CategoryPill(
                    name = item.subcategory.name,
                    color = item.subcategory.color
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.total.formatAmount(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}










