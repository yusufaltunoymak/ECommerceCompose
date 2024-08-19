package com.hoy.ecommercecompose.domain.usecase.payment

import com.hoy.ecommercecompose.ui.payment.PaymentContract
import javax.inject.Inject

class ValidateOrderUseCase @Inject constructor() {

    fun validate(order: PaymentContract.UiState): List<String> {
        val errors = mutableListOf<String>()

        if (order.cardHolderName.isBlank()) {
            errors.add("Card holder name is required.")
        }

        if (order.cardNumber.replace(" ", "").length != 16) {
            errors.add("Card number must be 16 digits.")
        }

        if (order.cvv.length != 3) {
            errors.add("CVV must be 3 digits.")
        }

        if (order.selectedMonth.isBlank() || order.selectedYear.isBlank()) {
            errors.add("Expiration date is required.")
        }

        if (order.selectedCity.isBlank()) {
            errors.add("City is required.")
        }

        if (order.selectedDistrict.isBlank()) {
            errors.add("District is required.")
        }

        if (order.addressText.isBlank()) {
            errors.add("Full address is required.")
        }

        return errors
    }
}