package com.hoy.ecommercecompose.ui.changepassword

import androidx.lifecycle.ViewModel
import com.hoy.ecommercecompose.ui.favorite.FavoriteContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ChangePasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordContract.UiState())
    val uiState: StateFlow<ChangePasswordContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<FavoriteContract.UiEffect>() }
    val uiEffect: Flow<FavoriteContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: ChangePasswordContract.UiAction) {
        when (action) {
            is ChangePasswordContract.UiAction.ChangePassword -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }

            is ChangePasswordContract.UiAction.ChangeConfirmPassword -> {
                _uiState.value =
                    _uiState.value.copy(confirmPassword = action.confirmPassword)
            }

            is ChangePasswordContract.UiAction.ResetPassword -> {
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
    }
}
