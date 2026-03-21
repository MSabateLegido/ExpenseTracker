package com.example.expensetracker.presentation.expenses.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.presentation.components.category.CategorySelectorDropdown
import com.example.expensetracker.presentation.components.expense.ExpenseDateField


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
                    modifier = Modifier.fillMaxWidth(),
                    categories = state.categories,
                    selectedSubcategory = state.selectedSubcategory,
                    onSubcategorySelected = {
                        onEvent(AddExpenseEvent.SubcategorySelected(it))
                    },
                    onAddCategoryClicked = {
                        onEvent(AddExpenseEvent.AddCategoryClicked)
                    },
                    hasAddCategory = true
                )
            }
        }

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
                    onValueChange = { input ->
                        limitTwoDecimals(input) {
                            onEvent(AddExpenseEvent.AmountChanged(input))
                        }
                    },
                    label = { Text(stringResource(id = R.string.add_expense_quantity_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(amountFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                )
            }
        }

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

fun limitTwoDecimals(input: String, changeTextEvent: () -> Unit) {
    val filtered = input.replace(",", ".")

    val regex = Regex("^\\d*\\.?\\d{0,2}$")

    if (filtered.isEmpty() || regex.matches(filtered)) {
        changeTextEvent()
    }
}


