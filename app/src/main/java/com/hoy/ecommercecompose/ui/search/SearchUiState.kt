package com.hoy.ecommercecompose.ui.search

import com.hoy.ecommercecompose.domain.model.ProductUi

data class SearchUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val productList: List<ProductUi> = emptyList()
)