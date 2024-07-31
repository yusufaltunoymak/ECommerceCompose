package com.hoy.ecommercecompose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.SignInWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginContract.LoginUiState())
    val loginUiState: StateFlow<LoginContract.LoginUiState> = _loginUiState

    fun onAction(action: LoginContract.LoginUiAction) {
        when (action) {
            is LoginContract.LoginUiAction.SignInClick -> signInWithEmailAndPassword()
            is LoginContract.LoginUiAction.ChangeEmail -> changeEmail(action.email)
            is LoginContract.LoginUiAction.ChangePassword -> changePassword(action.password)
        }
    }

    private fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            val state = loginUiState.value
            val resource = signInWithEmailAndPasswordUseCase(
                email = state.email,
                password = state.password
            )
            when (resource) {
                is Resource.Loading -> {
                    _loginUiState.value =
                        _loginUiState.value.copy(isLoading = true, isSignIn = false)
                }

                is Resource.Success -> {
                    _loginUiState.value =
                        _loginUiState.value.copy(isLoading = false, isSignIn = true)
                }

                is Resource.Error -> {
                    _loginUiState.value = _loginUiState.value.copy(
                        isLoading = false,
                        isSignIn = false
                    )
                }
            }
        }
    }

    private fun changeEmail(email: String) {
        _loginUiState.value = _loginUiState.value.copy(email = email)
    }

    private fun changePassword(password: String) {
        _loginUiState.value = _loginUiState.value.copy(password = password)
    }
}