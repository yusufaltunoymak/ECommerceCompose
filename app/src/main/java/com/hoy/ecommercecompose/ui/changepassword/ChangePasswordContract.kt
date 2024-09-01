package com.hoy.ecommercecompose.ui.changepassword

object ChangePasswordContract {
    data class UiState(
        val password: String = "",
        val confirmPassword: String = "",
        val currentPassword: String = "",
        val errorMessage: Int? = 0,
    )

    sealed class UiAction {
        data class ChangePassword(val password: String) : UiAction()
        data class ChangeCurrentPassword(val currentPassword: String) : UiAction()
        data class ChangeConfirmPassword(val confirmPassword: String) : UiAction()
        object ResetPassword : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: Int) : UiEffect()
    }
}
