package com.hoy.ecommercecompose.ui.changepassword

object ChangePasswordContract {
    data class UiState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false,
        val resetPasswordInProgress: Boolean = false,
        val resetPasswordSuccess: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data class ChangePassword(val password: String) : UiAction()
        data class ChangeConfirmPassword(val confirmPassword: String) : UiAction()
        object ResetPassword : UiAction()
    }
}
