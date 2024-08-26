package com.hoy.ecommercecompose.ui.notification

object NotificationContract {
    data class UiState(
        val isLoading: Boolean? = null,
        val notification: List<Any>? = null,
    )

    sealed class UiAction {
    }

    sealed class UiEffect {
    }
}