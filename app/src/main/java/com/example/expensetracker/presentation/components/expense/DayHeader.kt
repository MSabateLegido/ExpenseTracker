package com.example.expensetracker.presentation.components.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.utils.formatAmount
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DayHeader(
    day: LocalDate,
    total: Double
) {
    val formatter = remember {
        DateTimeFormatter.ofPattern("d MMM")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day.format(formatter),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = total.formatAmount(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}