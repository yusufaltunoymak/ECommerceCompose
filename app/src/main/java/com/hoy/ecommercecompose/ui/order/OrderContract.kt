package com.hoy.ecommercecompose.ui.order

import com.hoy.ecommercecompose.data.source.local.payment.PaymentWithProducts

object OrderContract {
    data class UiState(
        val isLoading: Boolean = false,
        val orders: List<PaymentWithProducts> = emptyList(),
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data object LoadOrders : UiAction()
        data class Retry(val userId: String) : UiAction() // Hata durumunda tekrar denemek için
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
    }
}
