package com.hoy.ecommercecompose.ui.category

import com.hoy.ecommercecompose.domain.model.ProductUi

data class CategoryUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categoryList: List<ProductUi> = emptyList(),
)