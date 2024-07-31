package com.hoy.ecommercecompose.ui.login

object LoginContract {
    data class LoginUiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val isSignIn: Boolean = false,
    )

    sealed class LoginUiAction {
        data object SignInClick : LoginUiAction()
        data class ChangeEmail(val email: String) : LoginUiAction()
        data class ChangePassword(val password: String) : LoginUiAction()
    }
}