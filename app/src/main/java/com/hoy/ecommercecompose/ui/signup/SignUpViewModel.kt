package com.hoy.ecommercecompose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpContract.UiState())
    val uiState: StateFlow<SignUpContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignUpContract.UiEffect>() }
    val uiEffect: Flow<SignUpContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: SignUpContract.UiAction) {
        when (action) {
            is SignUpContract.UiAction.SignUpClick -> signUp()
            is SignUpContract.UiAction.ChangeName -> updateUiState { copy(name = action.name) }
            is SignUpContract.UiAction.ChangeEmail -> updateUiState { copy(email = action.email) }
            is SignUpContract.UiAction.ChangePassword -> updateUiState { copy(password = action.password) }
            is SignUpContract.UiAction.ChangeSurname -> updateUiState { copy(surname = action.surname) }
            SignUpContract.UiAction.ClearError -> updateUiState { copy(signUpError = "") }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            val state = uiState.value
            val resource = createUserWithEmailAndPasswordUseCase(
                name = state.name,
                surname = state.surname,
                email = state.email,
                password = state.password,
                address = state.address
            )

            when (resource) {
                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true) }
                }

                is Resource.Success -> {
                    emitUiEffect(SignUpContract.UiEffect.GoToMainScreen)
                }

                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, signUpError = resource.message) }
                    emitUiEffect(SignUpContract.UiEffect.ShowAlertDialog)
                }
            }
        }
    }

    private fun updateUiState(block: SignUpContract.UiState.() -> SignUpContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: SignUpContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}
