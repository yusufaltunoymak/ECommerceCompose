package com.hoy.ecommercecompose.ui.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    private var _uiState: MutableStateFlow<NotificationContract.UiState> =
        MutableStateFlow(NotificationContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<NotificationContract.UiEffect>() }
    val uiEffect: Flow<NotificationContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: NotificationContract.UiAction) {
        when (action) {
            else -> {}
        }
    }
}
