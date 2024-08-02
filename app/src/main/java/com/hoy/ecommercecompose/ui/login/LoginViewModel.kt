package com.hoy.ecommercecompose.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.SignInWithEmailAndPasswordUseCase
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginContract.LoginUiState())
    val loginUiState: StateFlow<LoginContract.LoginUiState> = _loginUiState

    fun onAction(action: LoginContract.LoginUiAction) {
        when (action) {
            is LoginContract.LoginUiAction.SignInClick -> signInWithEmailAndPassword()
            is LoginContract.LoginUiAction.ChangeEmail -> changeEmail(action.email)
            is LoginContract.LoginUiAction.ChangePassword -> changePassword(action.password)
            is LoginContract.LoginUiAction.GoogleSignInClick -> initiateGoogleSignIn()
            is LoginContract.LoginUiAction.GoogleSignInResult -> handleGoogleSignInResult(action.intent)
        }
    }

    private fun initiateGoogleSignIn() {
        viewModelScope.launch {
            val intentSender = googleAuthUiClient.signIn()
            _loginUiState.update { it.copy(googleSignInRequest = intentSender) }
        }
    }

    private fun handleGoogleSignInResult(intent: Intent) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signInWithIntent(intent)
            if (result.data != null) {
                _loginUiState.update { it.copy(isSignIn = true) }
            } else {
                _loginUiState.update { it.copy(signInError = result.errorMessage) }
            }
        }
    }

    private fun signInWithEmailAndPassword() {
        val state = loginUiState.value
        if (validateInputs(state.email, state.password)) {
            viewModelScope.launch {
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
                            isSignIn = false,
                            signInError = resource.message
                        )
                    }
                }
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        val emailError =
            email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordError = password.isEmpty()

        _loginUiState.update {
            it.copy(
                showEmailError = emailError,
                showPasswordError = passwordError
            )
        }
        return !(emailError || passwordError)
    }

    private fun changeEmail(email: String) {
        _loginUiState.value = _loginUiState.value.copy(email = email, showEmailError = false)
    }

    private fun changePassword(password: String) {
        _loginUiState.value =
            _loginUiState.value.copy(password = password, showPasswordError = false)
    }
}