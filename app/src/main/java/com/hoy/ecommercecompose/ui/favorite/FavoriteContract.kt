package com.hoy.ecommercecompose.ui.favorite

import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.domain.model.ProductUi

object FavoriteContract {
    data class UiState(
        val isLoading: Boolean = false,
        val favoriteProducts: List<ProductUi> = emptyList(),
        val deleteFromFavorites: FavoriteResponse? = null,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data class DeleteFromFavorites(val productId: Int) : UiAction()
        data class LoadFavorites(val favoriteProducts: List<ProductUi>) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data class FavoriteProductDetailClick(val productId: Int) : UiEffect()
        data object BackScreen : UiEffect()
    }
}
