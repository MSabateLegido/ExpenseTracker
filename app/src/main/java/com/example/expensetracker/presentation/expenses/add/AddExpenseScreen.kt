package com.example.expensetracker.presentation.expenses.add

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.Subcategory


@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    state: AddExpenseState,
    onEvent: (AddExpenseEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Nom de la despesa
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(AddExpenseEvent.NameChanged(it)) },
            label = { Text("Nom de la despesa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Quantitat
        OutlinedTextField(
            value = state.amount,
            onValueChange = { onEvent(AddExpenseEvent.AmountChanged(it)) },
            label = { Text("Quantitat") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        // Selector de categoria
        Text(
            text = "Categoria",
            style = MaterialTheme.typography.titleMedium
        )

        CategorySelector(
            categories = state.categories,
            selectedSubcategory = state.selectedSubcategory,
            onSubcategorySelected = {
                onEvent(AddExpenseEvent.SubcategorySelected(it))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botó Guardar
        Button(
            onClick = { onEvent(AddExpenseEvent.SaveClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.name.isNotBlank()
                    && state.amount.isNotBlank()
                    && state.selectedSubcategory != null
                    && !state.isSaving
        ) {
            Text("Guardar despesa")
        }
    }
}

@Composable
fun CategorySelector(
    categories: List<CategoryWithChildren>,
    selectedSubcategory: Subcategory?,
    onSubcategorySelected: (Subcategory) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        categories.forEach { item ->

            // Categoria pare (només informativa)
            Text(
                text = item.category.name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Subcategories (seleccionables)
            item.subcategories.forEach { subcategory ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSubcategorySelected(subcategory)
                        }
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedSubcategory?.id == subcategory.id,
                        onClick = {
                            onSubcategorySelected(subcategory)
                        }
                    )
                    Text(subcategory.name)
                }
            }
        }
    }
}
