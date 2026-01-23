package com.example.expensetracker.presentation.categories

import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.Category
import kotlin.also
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    modifier: Modifier = Modifier,
    state: AddCategoryState,
    onEvent: (AddCategoryEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Afegir categoria") }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = state.isSaveEnabled,
                    onClick = { onEvent(AddCategoryEvent.SaveClicked) }
                ) {
                    Text("Guardar")
                }
            }
        }

    ) { paddingValues ->

        val scrollState = rememberScrollState()
        
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ─────────────────────────────────────
            // Categoria pare
            // ─────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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
                }
            }

            // ─────────────────────────────────────
            // Nova categoria (només si cal)
            // ─────────────────────────────────────
            if (state.isNewCategory) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Nova categoria",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Defineix la categoria pare abans d’afegir la subcategoria",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        NewCategoryFields(
                            name = state.categoryName,
                            selectedColor = state.categoryColor,
                            onNameChanged = {
                                onEvent(AddCategoryEvent.CategoryNameChanged(it))
                            },
                            onColorSelected = {
                                onEvent(AddCategoryEvent.CategoryColorChanged(it))
                            }
                        )
                    }
                }
            }

            // ─────────────────────────────────────
            // Subcategoria
            // ─────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Subcategoria",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "És la categoria que assignaràs a les despeses",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    SubcategoryFields(
                        name = state.subcategoryName,
                        selectedColor = state.subcategoryColor,
                        onNameChanged = {
                            onEvent(AddCategoryEvent.SubcategoryNameChanged(it))
                        },
                        onColorSelected = {
                            onEvent(AddCategoryEvent.SubcategoryColorChanged(it))
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(
    categories: List<Category>,
    selectedCategory: Category?,
    isNewCategory: Boolean,
    onExistingSelected: (Category) -> Unit,
    onNewCategorySelected: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text = "Categoria pare",
            style = MaterialTheme.typography.titleMedium
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                value = selectedCategory?.name
                    ?: if (isNewCategory) "Nova categoria" else "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoria") },
                leadingIcon = {
                    CategoryColorDot(
                        color = selectedCategory?.color
                            ?: MaterialTheme.colorScheme.outline
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CategoryColorDot(category.color)
                                Text(category.name)
                            }
                        },
                        onClick = {
                            expanded = false
                            onExistingSelected(category)
                        }
                    )
                }

                Divider()

                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Text("Crear nova categoria")
                        }
                    },
                    onClick = {
                        expanded = false
                        onNewCategorySelected()
                    }
                )
            }
        }
    }
}



@Composable
private fun CategoryColorDot(
    color: Color,
    size: Dp = 12.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(color, CircleShape)
    )
}

@Composable
private fun NewCategoryFields(
    name: String,
    selectedColor: Color,
    onNameChanged: (String) -> Unit,
    onColorSelected: (Color) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text = "Nova categoria",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Nom de la categoria") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
        )

        ColorPickerField(
            selectedColor = selectedColor,
            onColorSelected = onColorSelected
        )
    }
}

@Composable
private fun SubcategoryFields(
    name: String,
    selectedColor: Color,
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
        )

        ColorPickerField(
            selectedColor = selectedColor,
            onColorSelected = onColorSelected
        )
    }
}

@Composable
fun ColorPickerField(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(selectedColor, CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
        )

        OutlinedButton(onClick = { showDialog = true }) {
            Text("Escollir color")
        }
    }

    if (showDialog) {
        ColorPickerDialog(
            initialColor = selectedColor,
            onDismiss = { showDialog = false },
            onConfirm = {
                onColorSelected(it)
                showDialog = false
            }
        )
    }
}

@Composable
fun ColorPickerDialog2(
    initialColor: Color,
    onDismiss: () -> Unit,
    onConfirm: (Color) -> Unit
) {
    var hsv by remember {
        mutableStateOf(FloatArray(3).also {
            android.graphics.Color.colorToHSV(initialColor.toArgb(), it)
        })
    }

    val selectedColor = Color(android.graphics.Color.HSVToColor(hsv))

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecciona un color") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // 🎨 Hue slider
                Text("Tonalitat")
                Slider(
                    value = hsv[0],
                    onValueChange = { newValue ->  hsv = hsv.copyOf().also { it[0] = newValue } },
                    valueRange = 0f..360f
                )

                // 🎚 Saturation
                Text("Saturació")
                Slider(
                    value = hsv[1],
                    onValueChange = { newValue ->  hsv = hsv.copyOf().also { it[1] = newValue } },
                    valueRange = 0f..1f
                )

                // ☀ Value / Lightness
                Text("Lluminositat")
                Slider(
                    value = hsv[2],
                    onValueChange = { newValue -> hsv = hsv.copyOf().also { it[2] = newValue } },
                    valueRange = 0f..1f
                )

                // 👁 Preview
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(selectedColor, CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedColor) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel·lar")
            }
        }
    )
}


@Composable
fun HueSlider(
    hue: Float,
    onHueChanged: (Float) -> Unit
) {
    Slider(
        value = hue,
        onValueChange = onHueChanged,
        valueRange = 0f..360f,
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green,
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta,
                        Color.Red
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    )
}

@Composable
fun SaturationValuePicker(
    hue: Float,
    saturation: Float,
    value: Float,
    onChange: (Float, Float) -> Unit
) {
    val baseColor = Color.hsv(hue, 1f, 1f)
    val density = LocalDensity.current
    val cursorSize = 14.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()

        // 🎨 Gradient correcte:
        // - Horitzontal: Saturation (0 → 1)
        // - Vertical: Value (1 → 0)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.White, baseColor)
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val s = (offset.x / widthPx).coerceIn(0f, 1f)
                        val v = (1f - offset.y / heightPx).coerceIn(0f, 1f)
                        onChange(s, v)
                    }
                }
        )

        // 🎯 Cursor centrat correctament
        val cursorRadiusPx = with(density) { cursorSize.toPx() } / 2f

        val cursorXPx = (saturation * widthPx).coerceIn(0f, widthPx)
        val cursorYPx = ((1f - value) * heightPx).coerceIn(0f, heightPx)

        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { (cursorXPx - cursorRadiusPx).toDp() },
                    y = with(density) { (cursorYPx - cursorRadiusPx).toDp() }
                )
                .size(cursorSize)
                .border(2.dp, Color.White, CircleShape)
        )
    }
}



@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onDismiss: () -> Unit,
    onConfirm: (Color) -> Unit
) {
    var hsv by remember {
        mutableStateOf(FloatArray(3).also {
            android.graphics.Color.colorToHSV(initialColor.toArgb(), it)
        })
    }

    val selectedColor = Color.hsv(hsv[0], hsv[1], hsv[2])

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecciona un color") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                HueSlider(
                    hue = hsv[0],
                    onHueChanged = { newValue -> hsv = hsv.copyOf().also { it[0] = newValue } }
                )

                SaturationValuePicker(
                    hue = hsv[0],
                    saturation = hsv[1],
                    value = hsv[2]
                ) { s, v ->
                    hsv = hsv.copyOf().also {
                        it[1] = s
                        it[2] = v
                    }
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(selectedColor, CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedColor) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel·lar")
            }
        }
    )
}






