package com.hoy.ecommercecompose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpContract.UiState())
    val signUpUiState: StateFlow<SignUpContract.UiState> = _signUpUiState

    fun onAction(action: SignUpContract.UiAction) {
        when (action) {
            is SignUpContract.UiAction.SignUpClick -> signUp()
            is SignUpContract.UiAction.ChangeName -> changeName(action.name)
            is SignUpContract.UiAction.ChangeEmail -> changeEmail(action.email)
            is SignUpContract.UiAction.ChangePassword -> changePassword(action.password)
            is SignUpContract.UiAction.ChangeSurname -> changeSurname(action.surname)
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            val state = signUpUiState.value
            createUserWithEmailAndPasswordUseCase(state.name, state.surname,state.email, state.password, state.address).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _signUpUiState.value = _signUpUiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _signUpUiState.value = _signUpUiState.value.copy(isLoading = false)
                    }
                    is Resource.Error -> {
                        _signUpUiState.value = _signUpUiState.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun changeName(name : String) {
        _signUpUiState.value = _signUpUiState.value.copy(name = name)
    }

    private fun changeSurname(surname : String) {
        _signUpUiState.value = _signUpUiState.value.copy(surname = surname)
    }

    private fun changeEmail(email : String) {
        _signUpUiState.value = _signUpUiState.value.copy(email = email)
    }

    private fun changePassword(password : String) {
        _signUpUiState.value = _signUpUiState.value.copy(password = password)
    }

    fun changeAddress(address : String) {
        _signUpUiState.value = _signUpUiState.value.copy(address = address)
    }

}
