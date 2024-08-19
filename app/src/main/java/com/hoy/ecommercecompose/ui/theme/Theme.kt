package com.hoy.ecommercecompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object ECommerceComposeTheme {
    val colors: SampleProjectColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

@Composable
fun ECommerceComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides if (darkTheme) darkColors() else lightColors()
    ) {
        content()
    }
}
