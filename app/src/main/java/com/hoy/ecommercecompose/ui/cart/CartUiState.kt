package com.hoy.ecommercecompose.ui.cart

import com.hoy.ecommercecompose.data.source.remote.model.ProductDto

data class CartUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cartProductDtoList: List<ProductDto> = emptyList(),
    val discountCode: String = "",
    val totalCartPrice: Double = 0.0,
    val totalCartCount: Int = 0,
)
