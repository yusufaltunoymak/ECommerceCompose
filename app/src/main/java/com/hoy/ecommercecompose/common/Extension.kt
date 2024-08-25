package com.hoy.ecommercecompose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.text.SimpleDateFormat
import java.util.Locale

fun Int?.orEmpty(): Int {
    return this ?: 0
}

fun Double?.orEmpty(): Double {
    return this ?: 0.0
}

fun Boolean?.orEmpty(): Boolean {
    return this ?: false
}

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    this.then(
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    )
}

fun Long.formatDateWithTime(): String {
    val sdf =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(this)
}
