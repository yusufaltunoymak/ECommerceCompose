package com.hoy.ecommercecompose.ui.login

import android.content.Intent
import android.content.IntentSender
import com.hoy.ecommercecompose.ui.signup.SignUpContract.UiAction

object LoginContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val isSignIn: Boolean = false,
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false,
        val signInError: String = "",
        val googleSignInRequest: IntentSender? = null
    )

    sealed class UiAction {
        data object SignInClick : UiAction()
        data class ChangeEmail(val email: String) : UiAction()
        data class ChangePassword(val password: String) : UiAction()
        data class GoogleSignInResult(val intent: Intent) : UiAction()
        data object GoogleSignInClick : UiAction()
        data object ClearError : UiAction()
    }

    sealed class UiEffect {
        data object ShowAlertDialog : UiEffect()
        data object GoToHome : UiEffect()
        data object GoToForgotPasswordClick : UiEffect()
        data object GoToWelcomeScreen : UiEffect()
    }
}
