package com.hoy.ecommercecompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class ECFontSize {
    val small: TextUnit
        @Composable
        get() = 12.sp
    val medium: TextUnit
        @Composable
        get() = 16.sp
    val large: TextUnit
        @Composable
        get() = 20.sp
    val extraLarge: TextUnit
        @Composable
        get() = 24.sp
    val sizeTitle: TextUnit
        @Composable
        get() = 32.sp
    val subtitle: TextUnit
        @Composable
        get() = 28.sp
    val body: TextUnit
        @Composable
        get() = 18.sp
}

internal val LocalFontSizes = staticCompositionLocalOf { ECFontSize() }
