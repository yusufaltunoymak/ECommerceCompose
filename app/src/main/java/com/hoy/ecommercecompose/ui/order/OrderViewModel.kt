package com.hoy.ecommercecompose.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.order.GetOrdersWithProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrdersWithProductsUseCase: GetOrdersWithProductsUseCase,
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

    private fun loadOrders() {
        viewModelScope.launch {
            val userId = firebaseAuthRepository.getUserId()
            getOrdersWithProductsUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                isLoading = false,
                                orders = resource.data,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        handleError(resource.message)
                    }
                }
            }
        }
    }

    private suspend fun handleError(message: String) {
        updateUiState { copy(isLoading = false, errorMessage = message) }
        emitUiEffect(OrderContract.UiEffect.ShowError(message))
    }

    private fun updateUiState(block: OrderContract.UiState.() -> OrderContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: OrderContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}