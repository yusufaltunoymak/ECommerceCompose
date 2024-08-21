package com.hoy.ecommercecompose.ui.resetpassword

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ResetPasswordViewModel : ViewModel() {

    private val _resetUiState: MutableStateFlow<ResetPasswordContract.ResetPasswordUiState> =
        MutableStateFlow(ResetPasswordContract.ResetPasswordUiState())
    val resetUiState = _resetUiState.asStateFlow()

    fun onAction(action: ResetPasswordContract.ResetPasswordUiAction) {
        when (action) {
            is ResetPasswordContract.ResetPasswordUiAction.ChangeEmail -> {
                _resetUiState.value = _resetUiState.value.copy(email = action.email)
            }

            is ResetPasswordContract.ResetPasswordUiAction.ChangePassword -> {
                _resetUiState.value = _resetUiState.value.copy(password = action.password)
            }

            is ResetPasswordContract.ResetPasswordUiAction.ChangeConfirmPassword -> {
                _resetUiState.value =
                    _resetUiState.value.copy(confirmPassword = action.confirmPassword)
            }

            is ResetPasswordContract.ResetPasswordUiAction.ResetPassword -> {
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
    }
}
