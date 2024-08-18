package com.hoy.ecommercecompose.ui.payment

import com.hoy.ecommercecompose.data.source.local.ProductEntity

object PaymentContract {
    data class UiState(
        val isLoading: Boolean = false,
        val cardHolderName: String = "",
        val cardNumber: String = "",
        val expiryDate: String = "",
        val cvv: String = "",
        val selectedMonth: String = "",
        val selectedYear: String = "",
        val isMonthDropdownExpanded: Boolean = false,
        val isYearDropdownExpanded: Boolean = false,
        val showCardNumberError: Boolean = false,
        val showCvvError: Boolean = false,
        val showCardHolderError: Boolean = false,
        val showExpiryDateError: Boolean = false,
        val selectedCity: String = "",
        val selectedDistrict: String = "",
        val addressText: String = "",
        val isCityDropdownExpanded: Boolean = false,
        val isDistrictDropdownExpanded: Boolean = false,
        val productEntity: ProductEntity? = null,
        val validationErrors: List<String> = emptyList(),
        val cartProducts: List<ProductEntity> = emptyList()
    )

    sealed class UiAction {
        data class ChangeCardHolderName(val name: String) : UiAction()
        data class ChangeCardNumber(val number: String) : UiAction()
        data class ChangeCvv(val cvv: String) : UiAction()
        data class ChangeExpiryDate(val month: String, val year: String) : UiAction()
        data object ToggleMonthDropdown : UiAction()
        data object ToggleYearDropdown : UiAction()
        data class ChangeCity(val city: String) : UiAction()
        data class ChangeDistrict(val district: String) : UiAction()
        data class ChangeAddressText(val address: String) : UiAction()
        data object ToggleCityDropdown : UiAction()
        data object ToggleDistrictDropdown : UiAction()
        data object ClearError : UiAction()
        data object OrderClick : UiAction()
    }

    sealed class UiEffect {
        data class ShowAlertDialog(val message: String) : UiEffect()
        data object BackScreen : UiEffect()
        data class ShowOrderConfirmation(val message: Int) : UiEffect()
    }
}
