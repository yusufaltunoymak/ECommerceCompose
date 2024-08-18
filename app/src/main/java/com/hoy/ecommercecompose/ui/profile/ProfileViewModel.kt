package com.hoy.ecommercecompose.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private var _uiState: MutableStateFlow<ProfileContract.UiState> =
        MutableStateFlow(ProfileContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ProfileContract.UiEffect>() }
    val uiEffect: Flow<ProfileContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: ProfileContract.UiAction) {
        when (action) {
            ProfileContract.UiAction.BackButtonClick -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEffect.send(ProfileContract.UiEffect.NavigateBack)
        }
    }

}