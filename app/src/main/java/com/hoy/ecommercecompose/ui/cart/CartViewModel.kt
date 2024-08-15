package com.hoy.ecommercecompose.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.cart.DeleteProductFromCartUseCase
import com.hoy.ecommercecompose.domain.usecase.cart.GetCartProductsLocalUseCase
import com.hoy.ecommercecompose.domain.usecase.cart.UpdateCartProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsLocalUseCase,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val deleteProductFromCartUseCase: DeleteProductFromCartUseCase,
    private val updateCartProductUseCase: UpdateCartProductUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<CartContract.UiState> =
        MutableStateFlow(CartContract.UiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: CartContract.UiAction) {
        when (action) {
            is CartContract.UiAction.GetCartProducts -> getCartProducts()
            is CartContract.UiAction.DeleteProductFromCart -> deleteProductFromCart(action.id)
            is CartContract.UiAction.IncreaseQuantity ->  increaseQuantity(action.id)
            is CartContract.UiAction.DecreaseQuantity -> decreaseQuantity(action.id)
        }
    }

    fun getCartProducts() {
        viewModelScope.launch {
            getCartProductsUseCase(firebaseAuthRepository.getUserId()).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(cartProductList = response.data)
                        }
                        updateTotalPrice()
                    }

                    is Resource.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(errorMessage = response.message)
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(isLoading = true)
                        }
                    }

                }
            }
        }
    }

    private fun deleteProductFromCart(id: Int) {
        viewModelScope.launch {
            deleteProductFromCartUseCase(id)
            updateTotalPrice()
        }
    }

    private fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            val product = uiState.value.cartProductList.find { it.productId == id }
            product?.let {
                val updatedProduct = it.copy(quantity = it.quantity + 1)
                updateCartProduct(updatedProduct)
                updateTotalPrice()
            }
        }
    }

    private fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            val product = uiState.value.cartProductList.find { it.productId == id }
            product?.let {
                if (it.quantity > 1) {
                    val updatedProduct = it.copy(quantity = it.quantity - 1)
                    updateCartProduct(updatedProduct)
                    updateTotalPrice()
                }
            }
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = uiState.value.cartProductList.sumOf { it.price * it.quantity }
        _uiState.update { uiState ->
            uiState.copy(totalCartPrice = totalPrice)
        }
        updateTotalCount()
    }

    private fun updateTotalCount() {
        val totalCount = uiState.value.cartProductList.sumOf { it.quantity }
        _uiState.update { uiState ->
            uiState.copy(totalCartCount = totalCount)
        }
    }

    private fun updateCartProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            updateCartProductUseCase(productEntity)
        }
    }
}