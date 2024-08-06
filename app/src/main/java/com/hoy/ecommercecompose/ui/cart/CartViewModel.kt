package com.hoy.ecommercecompose.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.product.GetCartProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<CartUiState> =
        MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        if (userId.isNotEmpty()) {
            getCartProducts(userId)
        } else {
            _uiState.value = _uiState.value.copy(
                errorMessage = "User not authenticated",
                isLoading = false
            )
        }
    }


    private fun getCartProducts(userId: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }

            when (val result = getCartProductsUseCase(userId)) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            cartProductDtoList = result.data?.productDtos ?: emptyList(),
                            totalCartPrice = result.data?.productDtos?.sumOf { it.price ?: 0.0 } ?: 0.0,
                            totalCartCount = result.data?.productDtos?.size ?: 0,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                    Log.e("CartViewModel", "getCartProducts: ${result.message}")
                }

                is Resource.Loading -> {
                    _uiState.update { currentState ->
                        currentState.copy(isLoading = true)
                    }
                }
            }
        }
    }
}