package com.hoy.ecommercecompose.ui.category

import com.hoy.ecommercecompose.domain.model.ProductUi

object CategoryContract {
    data class UiState(
        val categoryList: List<ProductUi> = emptyList(),
        var searchQuery: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    )

    sealed class UiAction {
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
    }
}
