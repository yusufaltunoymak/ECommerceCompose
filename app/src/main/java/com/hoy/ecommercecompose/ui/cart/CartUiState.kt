package com.hoy.ecommercecompose.ui.cart

import com.hoy.ecommercecompose.data.source.local.ProductEntity

data class CartUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cartProductList: List<ProductEntity> = emptyList(),
    val discountCode: String = "",
    val totalCartPrice: Double = 0.0,
    val totalCartCount: Int = 0,
)
