package com.hoy.ecommercecompose.common

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Int?.orEmpty(): Int {
    return this ?: 0
}

fun Double?.orEmpty(): Double {
    return this ?: 0.0
}

fun Boolean?.orEmpty(): Boolean {
    return this ?: false
}
