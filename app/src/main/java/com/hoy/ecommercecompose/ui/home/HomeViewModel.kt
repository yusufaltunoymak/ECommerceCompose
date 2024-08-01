package com.hoy.ecommercecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)
            when (val result = getUserInformationUseCase()) {
                is Resource.Success -> {
                    _uiState.value = HomeUiState(currentUser = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    _uiState.value = HomeUiState(errorMessage = result.message, isLoading = false)
                }
                is Resource.Loading -> {
                    _uiState.value = HomeUiState(isLoading = true)
                }
            }
        }
    }
}