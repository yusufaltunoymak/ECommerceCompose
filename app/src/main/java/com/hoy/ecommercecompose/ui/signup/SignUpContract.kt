object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val name: String = "",
        val surname: String = "",
        val address: String = "",
        val isSignUp: Boolean = false,
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false,
        val showNameError: Boolean = false,
        val showSurnameError: Boolean = false,
        val signUpError: String? = null,
    )

    sealed class UiAction {
        data object SignUpClick : UiAction()
        data class ChangeEmail(val email: String) : UiAction()
        data class ChangePassword(val password: String) : UiAction()
        data class ChangeName(val name: String) : UiAction()
        data class ChangeSurname(val surname: String) : UiAction()
    }
}
