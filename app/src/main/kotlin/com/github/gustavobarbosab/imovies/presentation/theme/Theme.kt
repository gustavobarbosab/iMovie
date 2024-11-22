package com.github.gustavobarbosab.imovies.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TODO finish the setup of the schemes
private val DarkColorScheme = darkColorScheme(
    primary = Purple20,
    onPrimary = Purple60,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple60,
    inversePrimary = Purple40,
    secondary = Purple60,
    onSecondary = Purple20,
    secondaryContainer = Purple30,
    onSecondaryContainer = Purple60,
    tertiary = Purple20,
    onTertiary = Purple60,
    tertiaryContainer = Purple30,
    onTertiaryContainer = Purple60,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey20,
    onSurface = Grey70,
    inverseSurface = Grey30,
    inverseOnSurface = Grey90,
    surfaceVariant = Grey95,
    onSurfaceVariant = Grey10,
    outline = Purple40
)

private val LightColorScheme = darkColorScheme(
    primary = Purple20,
    onPrimary = Purple60,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple60,
    inversePrimary = Purple40,
    secondary = Purple60,
    onSecondary = Purple20,
    secondaryContainer = Purple30,
    onSecondaryContainer = Purple60,
    tertiary = Purple20,
    onTertiary = Purple60,
    tertiaryContainer = Purple30,
    onTertiaryContainer = Purple60,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey95,
    onBackground = Grey20,
    surface = Grey90,
    onSurface = Grey50,
    inverseSurface = Grey70,
    inverseOnSurface = Grey50,
    surfaceVariant = Grey95,
    onSurfaceVariant = Color.White,
    outline = Purple40
)


@Composable
fun IMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}