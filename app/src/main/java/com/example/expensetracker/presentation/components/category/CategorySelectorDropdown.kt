package com.example.expensetracker.presentation.components.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.domain.model.category.CategoryWithChildren
import com.example.expensetracker.domain.model.category.Subcategory
import com.example.expensetracker.presentation.components.utils.ColorDot
import kotlin.collections.forEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectorDropdown(
    categories: List<CategoryWithChildren>,
    selectedSubcategory: Subcategory?,
    onSubcategorySelected: (Subcategory) -> Unit,
    modifier: Modifier = Modifier,
    hasAddCategory: Boolean = false,
    onAddCategoryClicked: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var expandedCategoryId by remember { mutableStateOf<Long?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = modifier
                .menuAnchor(),
            readOnly = true,
            value = selectedSubcategory?.name ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.add_expense_category_card_title)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    modifier = Modifier.rotate(if (expanded) 180f else 0f),
                    contentDescription = null
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                expandedCategoryId = null
            }
        ) {

            categories.forEach { categoryWithChildren ->
                val expandedCategory =
                    expandedCategoryId == categoryWithChildren.category.id

                DropdownMenuItem(
                    onClick = {
                        expandedCategoryId =
                            if (expandedCategory) null
                            else categoryWithChildren.category.id
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ColorDot(
                                color = categoryWithChildren.category.color,
                                size = 12.dp
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = categoryWithChildren.category.name,
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                modifier = Modifier.rotate(if (expandedCategory) 180f else 0f),
                                contentDescription = null
                            )
                        }
                    }
                )

                if (expandedCategory) {
                    categoryWithChildren.subcategories.forEach { subcategory ->
                        DropdownMenuItem(
                            onClick = {
                                onSubcategorySelected(subcategory)
                                expanded = false
                                expandedCategoryId = null
                            },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 24.dp)
                                ) {
                                    ColorDot(subcategory.color)
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = subcategory.name,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        )
                    }
                }
            }

            if (hasAddCategory) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                )

                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        expandedCategoryId = null
                        onAddCategoryClicked()
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.add_expense_add_new_category_button))
                        }
                    }
                )
            }
        }

    }
}