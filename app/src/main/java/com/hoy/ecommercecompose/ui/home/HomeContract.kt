package com.hoy.ecommercecompose.ui.home

import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.data.source.remote.model.User
import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.domain.model.ProductUi

object HomeContract {
    data class UiState(
        val isLoading: Boolean? = null,
        val currentUser: User? = null,
        val errorMessage: String? = null,
        val categoryList: List<Category> = emptyList(),
        val productList: List<ProductUi> = emptyList(),
        val specialProductList: List<ProductUi> = emptyList(),
        val addToFavorites: FavoriteResponse? = null,
        val deleteFromFavorites: FavoriteResponse? = null
    )

    sealed class UiAction {
        data class ToggleFavoriteClick(val product: ProductUi) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data class ProductCartClick(val id: Int) : UiEffect()
        data object SearchClick : UiEffect()
        data class CategoryClick(val category: String) : UiEffect()
    }
}
