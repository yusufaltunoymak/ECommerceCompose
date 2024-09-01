package com.hoy.ecommercecompose.ui.cart

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.cart.DeleteProductFromCartUseCase
import com.hoy.ecommercecompose.domain.usecase.cart.GetCartProductsLocalUseCase
import com.hoy.ecommercecompose.domain.usecase.cart.UpdateCartProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsLocalUseCase,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val deleteProductFromCartUseCase: DeleteProductFromCartUseCase,
    private val updateCartProductUseCase: UpdateCartProductUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<CartContract.UiState> =
        MutableStateFlow(CartContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<CartContract.UiEffect>() }
    val uiEffect: Flow<CartContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: CartContract.UiAction) {
        when (action) {
            is CartContract.UiAction.GetCartProducts -> getCartProducts()
            is CartContract.UiAction.DeleteProductFromCart -> deleteProductFromCart(action.id)
            is CartContract.UiAction.IncreaseQuantity -> increaseQuantity(action.id)
            is CartContract.UiAction.DecreaseQuantity -> decreaseQuantity(action.id)
            is CartContract.UiAction.OnDiscountCodeChange -> onDiscountCodeChange(action.newCode)
            is CartContract.UiAction.ApplyDiscount -> applyDiscount()
            is CartContract.UiAction.ShowDeleteConfirmation -> showDeleteConfirmation(action.id)
        }
    }

    fun getCartProducts() {
        viewModelScope.launch {
            getCartProductsUseCase(firebaseAuthRepository.getUserId()).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(cartProductList = response.data)
                        }
                        updateTotalPrice()
                    }

                    is Resource.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(errorMessage = response.message)
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun showDeleteConfirmation(id: Int) {
        viewModelScope.launch {
            _uiEffect.send(CartContract.UiEffect.ShowDeleteConfirmation(id))
        }
    }

    private fun deleteProductFromCart(id: Int) {
        viewModelScope.launch {
            deleteProductFromCartUseCase(id)
            updateTotalPrice()
        }
    }

    private fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            val product = uiState.value.cartProductList.find { it.productId == id }
            product?.let {
                val updatedProduct = it.copy(quantity = it.quantity + 1)
                updateCartProduct(updatedProduct)
                updateTotalPrice()
            }
        }
    }

    private fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            val product = uiState.value.cartProductList.find { it.productId == id }
            product?.let {
                if (it.quantity > 1) {
                    val updatedProduct = it.copy(quantity = it.quantity - 1)
                    updateCartProduct(updatedProduct)
                    updateTotalPrice()
                } else {
                    showDeleteConfirmation(it.productId)
                }
            }
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = uiState.value.cartProductList.sumOf { it.price * it.quantity }
        val discount = uiState.value.discountPrice
        _uiState.update { uiState ->
            uiState.copy(totalCartPrice = totalPrice - discount)
        }
        updateTotalCount()
    }

    private fun updateTotalCount() {
        val totalCount = uiState.value.cartProductList.sumOf { it.quantity }
        _uiState.update { uiState ->
            uiState.copy(totalCartCount = totalCount)
        }
    }

    private fun updateCartProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            updateCartProductUseCase(productEntity)
        }
    }

    private fun onDiscountCodeChange(newCode: String) {
        _uiState.update {
            if (newCode.isEmpty()) {
                val totalPrice =
                    it.cartProductList.sumOf { product -> product.price * product.quantity }
                it.copy(
                    discountCode = newCode,
                    discountPrice = DEFAULT_AMOUNT,
                    discountMessage = "",
                    discountMessageColor = Color.Unspecified,
                    totalCartPrice = totalPrice
                )
            } else {
                it.copy(discountCode = newCode)
            }
        }
    }

    companion object {
        private const val DISCOUNT_CODE_50 = "SEPETTE50"
        private const val DISCOUNT_CODE_100 = "SEPETTE100"
        private const val DISCOUNT_AMOUNT_50 = 50.0
        private const val DISCOUNT_AMOUNT_100 = 100.0
        private const val DEFAULT_AMOUNT = 0.0
    }

    private fun applyDiscount() {
        val totalPrice =
            uiState.value.cartProductList.sumOf { product -> product.price * product.quantity }

        val discount = when (uiState.value.discountCode.uppercase()) {
            DISCOUNT_CODE_50 -> DISCOUNT_AMOUNT_50
            DISCOUNT_CODE_100 -> DISCOUNT_AMOUNT_100
            else -> DEFAULT_AMOUNT
        }

        if (totalPrice < discount) {
            _uiState.update {
                it.copy(
                    discountPrice = DEFAULT_AMOUNT,
                    discountMessage = "The discount amount cannot exceed the basket amount.",
                    discountMessageColor = Color.Red
                )
            }
            return
        }

        val discountMessage = if (discount > 0) {
            "Discount applied ${discount.toInt()} $"
        } else {
            "Invalid discount code."
        }
        val discountColor = if (discount > 0) {
            Color.Green
        } else {
            Color.Red
        }

        _uiState.update {
            val totalPrice =
                it.cartProductList.sumOf { product -> product.price * product.quantity }
            it.copy(
                totalCartPrice = totalPrice - discount,
                discountPrice = discount,
                discountMessage = discountMessage,
                discountMessageColor = discountColor
            )
        }
    }
}
