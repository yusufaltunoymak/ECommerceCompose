package com.hoy.ecommercecompose.ui.cart

import androidx.compose.ui.graphics.Color
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val cartProductList: List<ProductEntity> = emptyList(),
        val product: ProductEntity? = null,
        val discountCode: String = "",
        val totalCartPrice: Double = 0.0,
        val totalCartCount: Int = 0,
        val discountPrice: Double = 0.0,
        val discountMessage: String = "",
        val discountMessageColor: Color = Color.Black,
    )

    sealed class UiAction {
        data class DeleteProductFromCart(val id: Int) : UiAction()
        data class GetCartProducts(val userId: String) : UiAction()
        data class IncreaseQuantity(val id: Int) : UiAction()
        data class DecreaseQuantity(val id: Int) : UiAction()
        data class OnDiscountCodeChange(val newCode: String) : UiAction()
        data object ApplyDiscount : UiAction()
        data class ShowDeleteConfirmation(val id: Int) : UiAction()
    }

    sealed class UiEffect {
        data class ShowDeleteConfirmation(val id: Int) : UiEffect()
        data object PaymentClick : UiEffect()
    }
}
