package com.github.gustavobarbosab.imovies.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    onPrimary = Orange20,
    primaryContainer = Orange30,
    onPrimaryContainer = Orange90,
    inversePrimary = Orange40,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Yellow80,
    onTertiary = Yellow20,
    tertiaryContainer = Yellow30,
    onTertiaryContainer = Yellow90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey20,
    onSurface = Grey90,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Grey95,
    onSurfaceVariant = Grey10,
    outline = Yellow40
)


@Composable
fun IMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // TODO change it
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}