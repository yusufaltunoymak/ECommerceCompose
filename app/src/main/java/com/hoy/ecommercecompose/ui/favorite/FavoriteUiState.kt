package com.hoy.ecommercecompose.ui.favorite

import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.domain.model.ProductUi

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favoriteProducts: List<ProductUi> = emptyList(),
    val deleteFromFavorites: FavoriteResponse? = null,
    val errorMessage : String? = null
)
