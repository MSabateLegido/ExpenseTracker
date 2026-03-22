package com.example.expensetracker.presentation.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.expensetracker.utils.contrastTextColor


enum class CategoryChipSize {
    Small, Medium, Large
}

@Composable
fun CategoryChip(
    name: String,
    color: Color,
    size: CategoryChipSize = CategoryChipSize.Small
) {
    val textColor = remember(color) {
        color.contrastTextColor()
    }

    val (horizontalPadding, verticalPadding, textStyle) = when (size) {
        CategoryChipSize.Small -> Triple(8.dp, 4.dp, MaterialTheme.typography.labelSmall)
        CategoryChipSize.Medium -> Triple(10.dp, 6.dp, MaterialTheme.typography.labelMedium)
        CategoryChipSize.Large -> Triple(14.dp, 8.dp, MaterialTheme.typography.bodyMedium)
    }

    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        Text(
            text = name,
            style = textStyle,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}