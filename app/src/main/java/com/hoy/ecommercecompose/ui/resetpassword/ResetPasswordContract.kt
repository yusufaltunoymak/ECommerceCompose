package com.hoy.ecommercecompose.ui.resetpassword

object ResetPasswordContract {
    data class ResetPasswordUiState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false,
        val resetPasswordInProgress: Boolean = false,
        val resetPasswordSuccess: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class ResetPasswordUiAction {
        data class ChangeEmail(val email: String) : ResetPasswordUiAction()
        data class ChangePassword(val password: String) : ResetPasswordUiAction()
        data class ChangeConfirmPassword(val confirmPassword: String) : ResetPasswordUiAction()
        data object ResetPassword : ResetPasswordUiAction()
    }
}
