package com.hoy.ecommercecompose.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.datasources.CityRepository
import com.hoy.ecommercecompose.data.mapper.mapToOrderedProductEntity
import com.hoy.ecommercecompose.data.mapper.toPaymentEntity
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.payment.AddOrderedProductsUseCase
import com.hoy.ecommercecompose.domain.usecase.payment.AddPaymentUseCase
import com.hoy.ecommercecompose.domain.usecase.payment.ValidateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val addPaymentUseCase: AddPaymentUseCase,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val validateOrderUseCase: ValidateOrderUseCase,
    private val addOrderedProductsUseCase: AddOrderedProductsUseCase,
    private val cityRepository: CityRepository
) : ViewModel() {
    private var _uiState: MutableStateFlow<PaymentContract.UiState> =
        MutableStateFlow(PaymentContract.UiState())
    val uiState: StateFlow<PaymentContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<PaymentContract.UiEffect>() }
    val uiEffect: Flow<PaymentContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        loadCities()
    }

    private fun loadCities() {
        val cities = cityRepository.getCities()
        updateUiState { copy(cities = cities) }
    }

    fun onAction(action: PaymentContract.UiAction) {
        when (action) {
            is PaymentContract.UiAction.ChangeCardHolderName -> {
                updateUiState { copy(cardHolderName = action.name) }
            }

            is PaymentContract.UiAction.ChangeCardNumber -> {
                updateUiState { copy(cardNumber = action.number) }
            }

            is PaymentContract.UiAction.ChangeCvv -> {
                updateUiState { copy(cvv = action.cvv) }
            }

            is PaymentContract.UiAction.ChangeExpiryDate -> {
                updateUiState {
                    copy(
                        selectedMonth = action.month,
                        selectedYear = action.year,
                        expiryDate = "${action.month}/${action.year}"
                    )
                }
            }

            is PaymentContract.UiAction.ToggleMonthDropdown -> {
                updateUiState { copy(isMonthDropdownExpanded = !isMonthDropdownExpanded) }
            }

            is PaymentContract.UiAction.ToggleYearDropdown -> {
                updateUiState { copy(isYearDropdownExpanded = !isYearDropdownExpanded) }
            }

            is PaymentContract.UiAction.ChangeCity -> {
                updateUiState { copy(selectedCity = action.city, selectedDistrict = "") }
            }

            is PaymentContract.UiAction.ToggleCityDropdown -> {
                updateUiState { copy(isCityDropdownExpanded = !isCityDropdownExpanded) }
            }

            is PaymentContract.UiAction.ChangeDistrict -> {
                updateUiState { copy(selectedDistrict = action.district) }
            }

            is PaymentContract.UiAction.ToggleDistrictDropdown -> {
                updateUiState { copy(isDistrictDropdownExpanded = !isDistrictDropdownExpanded) }
            }

            is PaymentContract.UiAction.ChangeAddressText -> {
                updateUiState { copy(addressText = action.address) }
            }

            is PaymentContract.UiAction.OrderClick -> {
                addOrder()
            }

            PaymentContract.UiAction.ClearError -> {
                clearErrors()
            }
        }
    }

    private fun addOrder() {
        viewModelScope.launch {
            val validationErrors = validateOrderUseCase.validate(uiState.value)
            if (validationErrors.isNotEmpty()) {
                updateUiState { copy(validationErrors = validationErrors) }
                _uiEffect.send(
                    PaymentContract.UiEffect.ShowAlertDialog(
                        validationErrors.joinToString(
                            "\n"
                        )
                    )
                )
                return@launch
            }
            val userId = firebaseAuthRepository.getUserId()
            val payment = uiState.value.toPaymentEntity(userId)
            val orderedProducts = uiState.value.cartProducts.map { product ->
                mapToOrderedProductEntity(product, payment.orderId)
            }
            addOrderedProductsUseCase(orderedProducts)

            addPaymentUseCase(payment, orderedProducts, userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Loading
                    }

                    is Resource.Success -> {
                        _uiEffect.send(PaymentContract.UiEffect.ShowOrderConfirmation(R.string.order_success_received))
                    }

                    is Resource.Error -> {
                        _uiEffect.send(PaymentContract.UiEffect.ShowAlertDialog("Order failed: ${resource.message}"))
                    }
                }
            }
        }
    }

    private fun clearErrors() {
        updateUiState {
            copy(
                showCardNumberError = false,
                showCvvError = false,
                showCardHolderError = false,
                showExpiryDateError = false,
                validationErrors = emptyList()
            )
        }
    }

    private fun updateUiState(block: PaymentContract.UiState.() -> PaymentContract.UiState) {
        _uiState.update(block)
    }
}
