package com.hoy.ecommercecompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class ECDimension {
    val zero: Dp
        @Composable
        get() = 0.dp
    val one: Dp
        @Composable
        get() = 1.dp
    val two: Dp
        @Composable
        get() = 2.dp
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
    val seventyTwo: Dp
        @Composable
        get() = 72.dp
    val sixty: Dp
        @Composable
        get() = 80.dp
    val oneHundred: Dp
        @Composable
        get() = 100.dp
    val oneHundredTwenty: Dp
        @Composable
        get() = 120.dp
    val oneHundredFifty: Dp
        @Composable
        get() = 150.dp
    val oneHundredSeventy: Dp
        @Composable
        get() = 170.dp
    val twoHundredTwenty: Dp
        @Composable
        get() = 220.dp
    val twoHundred: Dp
        @Composable
        get() = 200.dp
    val twoHundredSixty: Dp
        @Composable
        get() = 260.dp
}

internal val LocalDimensions = staticCompositionLocalOf { ECDimension() }
