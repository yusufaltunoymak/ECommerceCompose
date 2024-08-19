package com.hoy.ecommercecompose.ui.cart

import com.hoy.ecommercecompose.data.source.local.ProductEntity

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val cartProductList: List<ProductEntity> = emptyList(),
        val discountCode: String = "",
        val totalCartPrice: Double = 0.0,
        val totalCartCount: Int = 0,
    )

    sealed class UiAction {
        data class DeleteProductFromCart(val id: Int) : UiAction()
        data class GetCartProducts(val userId: String) : UiAction()
        data class IncreaseQuantity(val id: Int) : UiAction()
        data class DecreaseQuantity(val id: Int) : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object PaymentClick : UiEffect()
    }
}
