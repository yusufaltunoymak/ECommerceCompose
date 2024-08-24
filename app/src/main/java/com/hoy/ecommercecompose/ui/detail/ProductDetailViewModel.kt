package com.hoy.ecommercecompose.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductEntity
import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.cart.AddToCartLocalUseCase
import com.hoy.ecommercecompose.domain.usecase.cart.GetCartProductsLocalUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.AddToFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.DeleteFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.product.GetProductDetailUseCase
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
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    savedStateHandle: SavedStateHandle,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addToCartLocalUseCase: AddToCartLocalUseCase,
    private val getCartProductsLocalUseCase: GetCartProductsLocalUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<ProductDetailContract.UiState> =
        MutableStateFlow(ProductDetailContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ProductDetailContract.UiEffect>() }
    val uiEffect: Flow<ProductDetailContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        savedStateHandle.get<Int>("productId")?.let {
            getProductDetail(it)
        }
    }

    fun onAction(action: ProductDetailContract.UiAction) {
        when (action) {
            is ProductDetailContract.UiAction.ToggleFavoriteClick -> {
                toggleFavorite()
            }

            is ProductDetailContract.UiAction.AddToCartClick -> {
                addToCart(action.productDetail)
            }

            is ProductDetailContract.UiAction.BackButtonClick -> {
                navigateBack()
            }
        }
    }

    private fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            when (
                val resource =
                    getProductDetailUseCase(firebaseAuthRepository.getUserId(), productId)
            ) {
                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true) }
                }

                is Resource.Success -> {
                    updateUiState { copy(productDetail = resource.data, isLoading = false) }
                }

                is Resource.Error -> {
                    updateUiState { copy(errorMessage = resource.message) }
                }
            }
        }
    }

    private fun toggleFavorite() {
        val productDetail = _uiState.value.productDetail ?: return
        viewModelScope.launch {
            productDetail.id?.let {
                if (productDetail.isFavorite == true) {
                    deleteFavorite(productDetail.id)
                } else {
                    addFavorite(productDetail.id)
                }
            }
        }
    }

    private fun addFavorite(productId: Int) {
        viewModelScope.launch {
            val baseBody = BaseBody(
                userId = firebaseAuthRepository.getUserId(),
                productId = productId
            )
            addToFavoriteUseCase(baseBody).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                productDetail = productDetail?.copy(isFavorite = true),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = response.message, isLoading = false) }
                    }
                }
            }
        }
    }

    private fun deleteFavorite(productId: Int) {
        viewModelScope.launch {
            val deleteFromFavoriteBody = DeleteFromFavoriteBody(
                userId = firebaseAuthRepository.getUserId(),
                id = productId
            )
            deleteFavoriteUseCase(deleteFromFavoriteBody).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                productDetail = productDetail?.copy(isFavorite = false),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = response.message, isLoading = false) }
                    }
                }
            }
        }
    }

    private fun addToCart(product: ProductDetail) {
        viewModelScope.launch {
            Log.d("ProductDetailViewModel", "addToCart: $product")
            val userId = firebaseAuthRepository.getUserId()
            getCartProductsLocalUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val productExistsInCart = resource.data.any { it.productId == product.id }
                        if (productExistsInCart) {
                            _uiEffect.send(ProductDetailContract.UiEffect.ShowToastMessage("Product already in cart"))
                        } else {
                            addProductToCart(product)
                        }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = resource.message, isLoading = false) }
                    }
                }
            }
        }
    }

    private fun addProductToCart(product: ProductDetail) {
        viewModelScope.launch {
            Log.d("ProductDetailViewModel", "addProductToCart: $product")
            val productEntity = product.mapToProductEntity(
                userId = firebaseAuthRepository.getUserId(),
                productId = product.id ?: 0,
                category = "",
                count = 1,
                quantity = 1
            )
            addToCartLocalUseCase(productEntity).collect {
                when (it) {
                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateUiState { copy(isLoading = false) }
                        _uiEffect.send(ProductDetailContract.UiEffect.ShowToastMessage("Product added to cart"))
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = it.message, isLoading = false) }
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEffect.send(ProductDetailContract.UiEffect.NavigateBack)
        }
    }

    private fun updateUiState(block: ProductDetailContract.UiState.() -> ProductDetailContract.UiState) {
        _uiState.update(block)
    }
}
