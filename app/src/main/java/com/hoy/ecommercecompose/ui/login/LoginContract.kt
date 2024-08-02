package com.hoy.ecommercecompose.ui.login

import android.content.Intent
import android.content.IntentSender

object LoginContract {
    data class LoginUiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        var isSignIn: Boolean = false,
        val emailError: Boolean = false,
        val passwordError: Boolean = false,
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false,
        val signInError: String? = null,
        val googleSignInRequest: IntentSender? = null
    )

    sealed class LoginUiAction {
        data object SignInClick : LoginUiAction()
        data class ChangeEmail(val email: String) : LoginUiAction()
        data class ChangePassword(val password: String) : LoginUiAction()
        data class GoogleSignInResult(val intent: Intent) : LoginUiAction()
        data object GoogleSignInClick : LoginUiAction()
    }

}