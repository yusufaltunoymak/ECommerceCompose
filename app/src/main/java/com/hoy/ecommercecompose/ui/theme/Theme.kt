package com.hoy.ecommercecompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object ECTheme {
    val colors: ECProjectColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val dimensions: ECDimension
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val typography: ECFontSize
        @Composable
        @ReadOnlyComposable
        get() = LocalFontSizes.current
}

@Composable
fun ECTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides if (darkTheme) darkColors() else lightColors()
    ) {
        content()
    }
}
