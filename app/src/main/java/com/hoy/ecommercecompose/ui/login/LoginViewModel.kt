package com.hoy.ecommercecompose.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.SignInWithEmailAndPasswordUseCase
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginContract.UiState())
    val uiState: StateFlow<LoginContract.UiState> = _uiState

    private val _uiEffect by lazy { Channel<LoginContract.UiEffect>() }
    val uiEffect: Flow<LoginContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: LoginContract.UiAction) {
        when (action) {
            is LoginContract.UiAction.SignInClick -> signIn()
            is LoginContract.UiAction.ChangeEmail -> updateUiState { copy(email = action.email) }
            is LoginContract.UiAction.ChangePassword -> updateUiState { copy(password = action.password) }
            is LoginContract.UiAction.GoogleSignInClick -> initiateGoogleSignIn()
            is LoginContract.UiAction.GoogleSignInResult -> handleGoogleSignInResult(action.intent)
            LoginContract.UiAction.ClearError -> updateUiState { copy(signInError = "") }
        }
    }

    private fun initiateGoogleSignIn() {
        viewModelScope.launch {
            val intentSender = googleAuthUiClient.signIn()
            _uiState.update { it.copy(googleSignInRequest = intentSender) }
        }
    }

    private fun handleGoogleSignInResult(intent: Intent) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signInWithIntent(intent)
            if (result.data != null) {
                _uiState.update { it.copy(isSignIn = true) }
            } else {
                _uiState.update { it.copy(signInError = result.errorMessage.toString()) }
            }
        }
    }

    private fun signIn() {
        val state = uiState.value
        viewModelScope.launch {
            val resource = signInWithEmailAndPasswordUseCase(
                email = state.email,
                password = state.password
            )
            when (resource) {
                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true) }
                }

                is Resource.Success -> {
                    emitUiEffect(LoginContract.UiEffect.GoToHome)
                }

                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, signInError = resource.message) }
                    emitUiEffect(LoginContract.UiEffect.ShowAlertDialog)
                }
            }
        }
    }

    private fun updateUiState(block: LoginContract.UiState.() -> LoginContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: LoginContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}
