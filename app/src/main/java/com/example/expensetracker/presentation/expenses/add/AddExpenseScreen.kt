package com.example.expensetracker.presentation.expenses.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.domain.model.category.Subcategory
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    state: AddExpenseState,
    onEvent: (AddExpenseEvent) -> Unit
) {
    val amountFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ===== CARD: CATEGORIA =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.add_expense_category_card_title),
                    style = MaterialTheme.typography.titleMedium
                )

                CategorySelectorDropdown(
                    categories = state.categories,
                    selectedSubcategory = state.selectedSubcategory,
                    onSubcategorySelected = {
                        onEvent(AddExpenseEvent.SubcategorySelected(it))
                    },
                    onAddCategoryClicked = {
                        onEvent(AddExpenseEvent.AddCategoryClicked)
                    }
                )
            }
        }

        // ===== CARD: DESPESA =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.add_expense_expense_card_title),
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(AddExpenseEvent.NameChanged(it))
                    },
                    label = { Text(stringResource(id = R.string.add_expense_expense_name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            amountFocusRequester.requestFocus()
                        }
                    )
                )

                OutlinedTextField(
                    value = state.amount,
                    onValueChange = {
                        onEvent(AddExpenseEvent.AmountChanged(it))
                    },
                    label = { Text(stringResource(id = R.string.add_expense_quantity_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(amountFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }
        }

        // ===== CARD: DATA =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_expense_date_card_title),
                    style = MaterialTheme.typography.titleMedium
                )

                ExpenseDateField(
                    date = state.selectedDate,
                    onDateSelected = {
                        onEvent(AddExpenseEvent.DateChanged(it))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        /*Button(
            onClick = { onEvent(AddExpenseEvent.AddDummyExpense(state.categories)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = true
        ) {
            Text(
                text = "Guardar dummy",
                style = MaterialTheme.typography.titleSmall
            )
        }*/
        // ===== BOTÓ GUARDAR =====
        Button(
            onClick = { onEvent(AddExpenseEvent.SaveClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = state.isSaveEnabled
        ) {
            Text(
                text = stringResource(R.string.add_expense_save_expense_button),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectorDropdown(
    categories: List<CategoryWithChildren>,
    selectedSubcategory: Subcategory?,
    onSubcategorySelected: (Subcategory) -> Unit,
    onAddCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var expandedCategoryId by remember { mutableStateOf<Long?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
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

            // ===== Categories + subcategories =====
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDateField(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = remember {
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }

    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = date.format(formatter),
        onValueChange = {},
        modifier = modifier.fillMaxWidth(),
        readOnly = true,
        label = { Text(stringResource(R.string.add_expense_date_card_title)) },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.select_date_content_description)
                )
            }
        }
    )

    if (showDatePicker) {
        ExpenseDatePickerDialog(
            initialDate = date,
            onDateSelected = {
                onDateSelected(it)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                        onDateSelected(selectedDate)
                    }
                }
            ) {
                Text(stringResource(R.string.confirm_text))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_text))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}




@Composable
fun ColorDot(
    color: Color,
    size: Dp = 10.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}


