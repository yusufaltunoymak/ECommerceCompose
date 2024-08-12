package com.hoy.ecommercecompose.ui.detail

import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail

object ProductDetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val productDetail: ProductDetail? = null,
        val error: String? = null,
    )

    sealed class UiAction {
        data object ToggleFavoriteClick :UiAction()
    }

    sealed class UiEffect {
        data object ShowError : UiEffect()
        data object BackScreen : UiEffect()
//        data object ShareProduct : UiEffect()
    }
}
