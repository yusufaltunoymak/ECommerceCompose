package com.hoy.ecommercecompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class ECDimension {
    val zero: Dp
        @Composable
        get() = 0.dp
    val four: Dp
        @Composable
        get() = 4.dp
    val eight: Dp
        @Composable
        get() = 8.dp
    val twelve: Dp
        @Composable
        get() = 12.dp
    val sixteen: Dp
        @Composable
        get() = 16.dp
    val twenty: Dp
        @Composable
        get() = 20.dp
    val twentyFour: Dp
        @Composable
        get() = 24.dp
    val twentyEight: Dp
        @Composable
        get() = 28.dp
    val thirtyTwo: Dp
        @Composable
        get() = 32.dp
    val thirtySix: Dp
        @Composable
        get() = 36.dp
    val forty: Dp
        @Composable
        get() = 40.dp
    val oneHundredFifty: Dp
        @Composable
        get() = 150.dp
    val twoHundredTwenty: Dp
        @Composable
        get() = 220.dp
}

internal val LocalDimensions = staticCompositionLocalOf { ECDimension() }
