package com.hoy.ecommercecompose.ui.account

import com.hoy.ecommercecompose.data.source.remote.model.User

object AccountContract {
    data class UiState(
        val currentUser: User? = null,
        val name: String = "",
        val surname: String = "",
        val email: String = "",
        val address: String = "",
        val isSaveEnabled: Boolean = false,
        val isEditing: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class UiAction() {
        data class SaveUserInformation(val user: User) : UiAction()
        data class UpdateName(val name: String) : UiAction()
        data class UpdateSurname(val surname: String) : UiAction()
        data class UpdateEmail(val email: String) : UiAction()
        data class UpdateAddress(val address: String) : UiAction()
        data object ToggleEditing : UiAction()

    }

    sealed class UiEffect(){
        data class ChangePasswordClick(val id: Int) : UiEffect()
        data class NotificationClick(val id: Int) : UiEffect()
        data object NavigateToLogin : UiEffect()
    }
}
