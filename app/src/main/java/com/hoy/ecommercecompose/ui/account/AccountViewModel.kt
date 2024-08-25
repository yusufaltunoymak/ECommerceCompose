package com.hoy.ecommercecompose.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val firebaseAuthRepository: FirebaseAuthRepository,
) : ViewModel() {

    private var _uiState: MutableStateFlow<AccountContract.UiState> =
        MutableStateFlow(AccountContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<AccountContract.UiEffect>() }
    val uiEffect: Flow<AccountContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        getUserInformation()
    }

    fun onAction(action: AccountContract.UiAction) {
        when (action) {
            else -> {}
        }
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            when (val result = getUserInformationUseCase()) {
                is Resource.Success -> {
                    updateUiState { copy(currentUser = result.data) }
                }

                is Resource.Error -> {
                    updateUiState { copy(errorMessage = result.message) }
                }

                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true) }
                }
            }
        }
    }
    private fun updateUiState(block: AccountContract.UiState.() -> AccountContract.UiState) {
        _uiState.update(block)
    }
}
