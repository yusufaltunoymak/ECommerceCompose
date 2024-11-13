package com.hoy.ecommercecompose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Double?.orZeroDouble(): Double {
    return this ?: 0.0
}

fun Boolean?.orFalse(): Boolean {
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

@Composable
fun <T> Flow<T>.collectWithLifecycle(
    collect: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectWithLifecycle.collect { effect ->
                collect(effect)
            }
        }
    }
}
