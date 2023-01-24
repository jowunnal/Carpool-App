package com.mate.carpool.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle

private val LightColorPalette = lightColors(
    primary = primary50,
    primaryVariant = primary50,
    secondary = red50,
    background = white,
    surface = white,
    onPrimary = white,
    onSecondary = white,
    onBackground = black,
    onSurface = black,
)

@Composable
fun MateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyle provides TextStyle.Default.copy(fontFamily = notosanskr),
    ) {
        MaterialTheme(
            colors = LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}