package com.hoy.ecommercecompose.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.order.GetUserOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getUserOrdersUseCase: GetUserOrdersUseCase,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderContract.UiState())
    val uiState: StateFlow<OrderContract.UiState> = _uiState

    private val _uiEffect by lazy { Channel<OrderContract.UiEffect>() }
    val uiEffect: Flow<OrderContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        loadOrders()
    }

    fun onAction(action: OrderContract.UiAction) {
        when (action) {
            is OrderContract.UiAction.LoadOrders -> {
                loadOrders()
            }

            is OrderContract.UiAction.Retry -> {
                loadOrders()
            }
        }
    }

    fun loadOrders() {
        viewModelScope.launch {
            updateUiState { copy(errorMessage = null) }
            val userId = firebaseAuthRepository.getUserId()
            getUserOrdersUseCase(userId)
                .onStart { updateUiState { copy(isLoading = true) } }
                .onCompletion { updateUiState { copy(isLoading = false) } }
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val sortedOrders = resource.data.sortedByDescending { it.orderDate }
                            updateUiState { copy(isLoading = false, orders = sortedOrders) }
                        }

                        is Resource.Error -> {
                            updateUiState {
                                copy(
                                    isLoading = false,
                                    errorMessage = resource.message
                                )
                            }
                            emitUiEffect(OrderContract.UiEffect.ShowError(resource.message))
                        }
                    }
                }
        }
    }

    private fun updateUiState(block: OrderContract.UiState.() -> OrderContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: OrderContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}
