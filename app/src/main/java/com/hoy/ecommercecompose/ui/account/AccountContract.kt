package com.hoy.ecommercecompose.ui.account

import com.hoy.ecommercecompose.data.source.remote.model.User

object AccountContract {
    data class UiState(
        val currentUser: User? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    )

    sealed class UiAction() {
    }

    sealed class UiEffect() {
    }
}