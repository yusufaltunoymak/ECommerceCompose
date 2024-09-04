package com.hoy.ecommercecompose.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.User
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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
            is AccountContract.UiAction.UpdateName -> updateUiState {
                copy(name = action.name, isSaveEnabled = true)
            }

            is AccountContract.UiAction.UpdateSurname -> updateUiState {
                copy(surname = action.surname, isSaveEnabled = true)
            }

            is AccountContract.UiAction.UpdateEmail -> updateUiState {
                copy(email = action.email, isSaveEnabled = true)
            }

            is AccountContract.UiAction.UpdateAddress -> updateUiState {
                copy(address = action.address, isSaveEnabled = true)
            }

            is AccountContract.UiAction.ToggleEditing -> updateUiState {
                copy(isEditing = !isEditing)
            }

            is AccountContract.UiAction.LogOut -> signOut()

            is AccountContract.UiAction.SaveUserInformation -> saveUserInformation(action.user)
        }
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            getUserInformationUseCase()
                .onStart { updateUiState { copy(isLoading = true) } }
                .onCompletion { updateUiState { copy(isLoading = false) } }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val user = result.data
                            updateUiState {
                                copy(
                                    currentUser = user,
                                    name = user.name!!,
                                    surname = user.surname!!,
                                    email = user.email!!,
                                    address = user.address!!
                                )
                            }
                        }

                        is Resource.Error -> {
                            updateUiState { copy(errorMessage = result.message) }
                        }
                    }
                }
        }
    }

    private fun saveUserInformation(user: User) {
        viewModelScope.launch {
            firebaseAuthRepository.updateUserInformation(user)
                .onStart { updateUiState { copy(isLoading = true) } }
                .onCompletion { updateUiState { copy(isLoading = false) } }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            updateUiState { copy(currentUser = user) }
                        }

                        is Resource.Error -> {
                            updateUiState { copy(errorMessage = result.message) }
                        }
                    }
                }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            firebaseAuthRepository.signOut()
                .onStart { updateUiState { copy(isLoading = true) } }
                .onCompletion { updateUiState { copy(isLoading = false) } }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            updateUiState { copy(currentUser = null) }
                        }

                        is Resource.Error -> {
                            updateUiState { copy(errorMessage = it.message) }
                        }
                    }
                }
        }
    }

    private fun updateUiState(block: AccountContract.UiState.() -> AccountContract.UiState) {
        _uiState.update(block)
    }
}