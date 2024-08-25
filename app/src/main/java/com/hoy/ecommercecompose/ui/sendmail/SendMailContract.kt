package com.hoy.ecommercecompose.ui.sendmail

object SendMailContract {
    data class SendMailUiState(
        val email: String = "",
        val showEmailError: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class SendMailUiAction {
        data class SendEmailAction(val email: String) : SendMailUiAction()
        data object SendMail : SendMailUiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val messageResId: Int) : UiEffect()
        data object NavigateToLogin : UiEffect()
        data object BackClick : UiEffect()
    }
}
