package com.hoy.ecommercecompose.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val PrimaryColor = Color(0xFFEF8354)
val SecondaryColor = Color(0xFF2D3142)
val TertiaryColor = Color(0xFFEFB8C8)
val PrimaryDarkColor = Color(0xFFD76D77)

fun lightColors(
    primary: Color = Color(0xFFFF5722),
    primaryContainer: Color = Color(0xFF2D3142),
    secondary: Color = Color(0xFF2196F3),
    secondaryContainer : Color = Color(0xFF009688)
): SampleProjectColor = SampleProjectColor(
    primary = primary,
    primaryContainer = primaryContainer,
    secondary = secondary,
    secondaryContainer = secondaryContainer
)

fun darkColors(
    primary: Color = Color(0xFFFF5722),
    primaryContainer: Color = Color(0xFF2D3142),
    secondary: Color = Color(0xFF2196F3),
    secondaryContainer : Color = Color(0xFF009688)
): SampleProjectColor = SampleProjectColor(
    primary = primary,
    primaryContainer = primaryContainer,
    secondary = secondary,
    secondaryContainer = secondaryContainer
)


class SampleProjectColor(
    primary: Color,
    primaryContainer: Color,
    secondary: Color,
    secondaryContainer: Color
) {
    private var _primary: Color by mutableStateOf(primary)
    val primary: Color = _primary

    private var _primaryContainer: Color by mutableStateOf(primaryContainer)
    val primaryContainer: Color = _primaryContainer

    private var _secondary: Color by mutableStateOf(secondary)
    val secondary: Color = _secondary

    private var _secondaryContainer: Color by mutableStateOf(secondaryContainer)
    val secondaryContainer: Color = _secondaryContainer

    private class KelpColorPreview {
        val primary_FF000000_FFFFFFFF = Unit
        val primaryContainer_FFFFFFFF_FF000000 = Unit
    }

}
internal val LocalColors = staticCompositionLocalOf { lightColors() }