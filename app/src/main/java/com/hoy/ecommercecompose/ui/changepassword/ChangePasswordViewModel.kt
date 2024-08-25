package com.hoy.ecommercecompose.ui.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hoy.ecommercecompose.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChangePasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordContract.UiState())
    val uiState: StateFlow<ChangePasswordContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ChangePasswordContract.UiEffect>() }
    val uiEffect: Flow<ChangePasswordContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: ChangePasswordContract.UiAction) {
        when (action) {
            is ChangePasswordContract.UiAction.ChangePassword -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }

            is ChangePasswordContract.UiAction.ChangeConfirmPassword -> {
                _uiState.value = _uiState.value.copy(confirmPassword = action.confirmPassword)
            }

            is ChangePasswordContract.UiAction.ResetPassword -> {
                viewModelScope.launch {
                    resetPassword()
                }
            }
        }
    }

    private suspend fun resetPassword() {
        val currentState = _uiState.value
        if (currentState.password != currentState.confirmPassword) {
            _uiState.value = _uiState.value.copy(errorMessage = "Passwords do not match")
            return
        }

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        user?.let {
            try {
                it.updatePassword(currentState.password).await()
                _uiEffect.send(ChangePasswordContract.UiEffect.ShowToast(R.string.password_changed_message))

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        } ?: run {
            _uiState.value = _uiState.value.copy(errorMessage = "No user is signed in")
        }
    }
}
