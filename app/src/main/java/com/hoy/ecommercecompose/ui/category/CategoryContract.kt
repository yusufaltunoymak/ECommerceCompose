package com.hoy.ecommercecompose.ui.category

import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.login.LoginContract.UiAction

object CategoryContract {
    data class UiState(
        val categoryList: List<ProductUi> = emptyList(),
        var searchQuery: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    )

    sealed class UiAction {
        data object ClearError : UiAction()
        data class SearchProducts(val query: String) : UiAction()
        data class SortProducts(val sortOption: SortOption) : UiAction()
        data class LoadProducts(val categoryList: List<ProductUi>) : UiAction()
        data class ChangeQuery(val query: String) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
        data class DetailScreen(val id: Int) : UiEffect()
    }
}
