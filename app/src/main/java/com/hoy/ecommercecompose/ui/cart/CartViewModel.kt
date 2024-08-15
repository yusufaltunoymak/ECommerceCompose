package com.hoy.ecommercecompose.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.cart.GetCartProductsLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsLocalUseCase,
    firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    private var _uiState: MutableStateFlow<CartUiState> =
        MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCartProducts(userId = firebaseAuthRepository.getUserId())
    }

    private fun getCartProducts(userId: String) {
        viewModelScope.launch {
            val products = getCartProductsUseCase(userId)
            _uiState.update { uiState ->
                uiState.copy(cartProductList = products)
            }
        }
    }
}