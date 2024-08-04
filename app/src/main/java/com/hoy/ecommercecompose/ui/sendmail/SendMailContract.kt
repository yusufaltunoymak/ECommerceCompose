package com.hoy.ecommercecompose.ui.sendmail

object SendMailContract {
    data class SendMailUiState(
        val email: String = "",
        val showEmailError: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class SendMailUiAction {
        data class SendEmailAction(val email: String) : SendMailUiAction()
        object SendMail : SendMailUiAction()
    }
}