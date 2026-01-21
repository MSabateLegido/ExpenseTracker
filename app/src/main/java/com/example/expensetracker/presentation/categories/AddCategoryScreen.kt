package com.example.expensetracker.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.Category


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    state: AddCategoryState,
    onEvent: (AddCategoryEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Afegir categoria") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CategorySelector(
                categories = state.categories,
                selectedCategory = state.categoryParent,
                isNewCategory = state.isNewCategory,
                onExistingSelected = {
                    onEvent(AddCategoryEvent.ExistingCategorySelected(it))
                },
                onNewCategorySelected = {
                    onEvent(AddCategoryEvent.NewCategorySelected)
                }
            )

            if (state.isNewCategory) {
                NewCategoryFields(
                    name = state.categoryName,
                    onNameChanged = {
                        onEvent(AddCategoryEvent.CategoryNameChanged(it))
                    },
                    onColorSelected = {
                        onEvent(AddCategoryEvent.CategoryColorChanged(it))
                    }
                )
            }

            Divider()

            SubcategoryFields(
                name = state.subcategoryName,
                onNameChanged = {
                    onEvent(AddCategoryEvent.SubcategoryNameChanged(it))
                },
                onColorSelected = {
                    onEvent(AddCategoryEvent.SubcategoryColorChanged(it))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(AddCategoryEvent.SaveClicked) }
            ) {
                Text("Guardar")
            }
        }
    }
}

@Composable
private fun CategorySelector(
    categories: List<Category>,
    selectedCategory: Category?,
    isNewCategory: Boolean,
    onExistingSelected: (Category) -> Unit,
    onNewCategorySelected: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = "Categoria pare",
            style = MaterialTheme.typography.titleMedium
        )

        categories.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExistingSelected(category) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedCategory?.id == category.id && !isNewCategory,
                    onClick = { onExistingSelected(category) }
                )
                Text(category.name)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNewCategorySelected() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isNewCategory,
                onClick = onNewCategorySelected
            )
            Text("Crear nova categoria")
        }
    }
}

@Composable
private fun NewCategoryFields(
    name: String,
    onNameChanged: (String) -> Unit,
    onColorSelected: (Color) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = "Nova categoria",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Nom de la categoria") },
            modifier = Modifier.fillMaxWidth()
        )

        ColorPicker(onColorSelected)
    }
}

@Composable
private fun ColorPicker(
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Magenta
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color, shape = CircleShape)
                    .clickable { onColorSelected(color) }
            )
        }
    }
}


@Composable
private fun SubcategoryFields(
    name: String,
    onNameChanged: (String) -> Unit,
    onColorSelected: (Color) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = "Subcategoria",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Nom de la subcategoria") },
            modifier = Modifier.fillMaxWidth()
        )

        ColorPicker(onColorSelected)
    }
}


