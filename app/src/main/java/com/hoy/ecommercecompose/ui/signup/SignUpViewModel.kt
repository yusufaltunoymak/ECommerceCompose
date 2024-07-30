package com.hoy.ecommercecompose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _signUpViewState = MutableStateFlow(SignUpViewState())
    val signUpViewState: StateFlow<SignUpViewState> = _signUpViewState

    fun signUp(email: String, password: String, name: String, surname: String, address: String = "") {
        viewModelScope.launch {
            createUserWithEmailAndPasswordUseCase(email, password, name, surname, address).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _signUpViewState.value = SignUpViewState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _signUpViewState.value = SignUpViewState(isLoading = false, isSuccess = resource.data)
                    }
                    is Resource.Error -> {
                        _signUpViewState.value = SignUpViewState(isLoading = false, errorMessage = resource.message)
                    }
                }
            }
        }
    }
}
