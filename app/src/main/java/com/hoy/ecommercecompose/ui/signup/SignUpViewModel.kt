package com.hoy.ecommercecompose.ui.signup

import android.util.Log
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

    fun signUp(name: String, surname: String, email: String, password: String, address: String = "") {
        viewModelScope.launch {
            createUserWithEmailAndPasswordUseCase(name, surname, email, password, address).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _signUpViewState.value = SignUpViewState(isLoading = true)
                        Log.e("SignUpViewModel", "Loading")
                    }
                    is Resource.Success -> {
                        _signUpViewState.value = SignUpViewState(isLoading = false, isSuccess = resource.data)
                        Log.e("SignUpViewModel", "Success: ${resource.data}")
                    }
                    is Resource.Error -> {
                        _signUpViewState.value = SignUpViewState(isLoading = false, errorMessage = resource.message)
                        Log.e("SignUpViewModel", "Error: ${resource.message}")
                    }
                }
            }
        }
    }
}
