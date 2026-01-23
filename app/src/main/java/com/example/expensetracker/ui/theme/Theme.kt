package com.example.expensetracker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenPrimaryContainer,
    onPrimaryContainer = Color(0xFF0D3D17),

    secondary = BlueSecondary,
    onSecondary = Color.White,
    secondaryContainer = BlueSecondaryContainer,
    onSecondaryContainer = Color(0xFF00363A),

    tertiary = AmberTertiary,
    onTertiary = Color.Black,

    background = AppBackground,
    onBackground = Color(0xFF1C1B1F),

    surface = AppSurface,
    onSurface = Color(0xFF1C1B1F),

    surfaceVariant = Color(0xFFE1E5EA),
    onSurfaceVariant = Color(0xFF44474E),

    outline = Color(0xFFB0B7C3)
)

/*private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = Color(0x0700FF14),
    surface = Color(0x19000000),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)*/

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}