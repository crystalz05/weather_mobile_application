package com.tyro.weatherapplication.ui.theme

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


// Primary & Accent colors
val BluePrimary = Color(0xFF007AFF)       // Used for icons, buttons, highlights (+Add Location)
val BlueLight = Color(0xFF409CFF)         // Slightly lighter accent for hover/pressed states

// Light theme colors
val LightBackground = Color(0xFFF9FBFF)   // Screen background (light gray-blue)
val LightSurface = Color(0xFFFFFFFF)      // Card background
val LightOnSurface = Color(0xFF1C1C1E)    // Text color on cards
val LightSubText = Color(0xFF666666)      // For weather condition text
val LightDivider = Color(0xFFE5E5EA)      // For subtle borders/dividers

// Dark theme colors
val DarkBackground = Color(0xFF0D0D0D)    // Screen background
val DarkSurface = Color(0xFF1C1C1E)       // Card background
val DarkOnSurface = Color(0xFFEFEFEF)     // Main text
val DarkSubText = Color(0xFFB0B0B0)       // Weather condition text
val DarkDivider = Color(0xFF2C2C2E)       // Subtle borders

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueLight,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = LightOnSurface,
    onSurface = LightOnSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    secondary = BlueLight,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
)

@Composable
fun WeatherApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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