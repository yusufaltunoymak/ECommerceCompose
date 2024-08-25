package com.hoy.ecommercecompose.ui.search

import com.hoy.ecommercecompose.domain.model.ProductUi

object SearchContract {

    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val productList: List<ProductUi> = emptyList(),
        val searchQuery: String = ""
    )

    sealed class UiAction {
        data class ChangeQuery(val query: String) : UiAction()
        data class LoadProduct(val productList: List<ProductUi>) : UiAction()
    }

    sealed class UiEffect {
        data class GoToDetail(val productId: Int) : UiEffect()
        data object NavigateBack : UiEffect()
    }
}
