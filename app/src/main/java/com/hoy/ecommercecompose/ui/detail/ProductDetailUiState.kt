package com.hoy.ecommercecompose.ui.detail

import com.hoy.ecommercecompose.domain.model.ProductDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val productDetail: ProductDetail? = null,
    val error: String? = null,
)