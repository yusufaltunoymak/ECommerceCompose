package com.hoy.ecommercecompose.ui.login

object LoginContract {
    data class LoginUiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val isSignIn: Boolean = false,
        val emailError: Boolean = false,
        val passwordError: Boolean = false,
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false
    )

    sealed class LoginUiAction {
        data object SignInClick : LoginUiAction()
        data class ChangeEmail(val email: String) : LoginUiAction()
        data class ChangePassword(val password: String) : LoginUiAction()
    }

    fun handleAction(action: LoginContract.LoginUiAction, uiState: LoginContract.LoginUiState, onUiStateChange: (LoginContract.LoginUiState) -> Unit) {
        when (action) {
            is LoginContract.LoginUiAction.SignInClick -> {
                val emailError = uiState.email.isEmpty()
                val passwordError = uiState.password.isEmpty()
                onUiStateChange(uiState.copy(
                    showEmailError = emailError,
                    showPasswordError = passwordError
                ))
                if (!emailError && !passwordError) {
                }
            }
            is LoginContract.LoginUiAction.ChangeEmail -> onUiStateChange(uiState.copy(email = action.email))
            is LoginContract.LoginUiAction.ChangePassword -> onUiStateChange(uiState.copy(password = action.password))
        }
    }
}