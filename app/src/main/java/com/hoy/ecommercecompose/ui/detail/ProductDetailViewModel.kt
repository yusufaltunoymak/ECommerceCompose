package com.hoy.ecommercecompose.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.AddToFavoriteBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.favorite.AddToFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.DeleteFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.product.GetProductDetailUseCase
import com.hoy.ecommercecompose.ui.home.HomeContract
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
            is ProductDetailContract.UiAction.ToggleFavoriteClick -> { toggleFavorite() }
        }
    }

    private fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            when (val resource =
                getProductDetailUseCase(firebaseAuthRepository.getUserId(), productId)) {
                is Resource.Loading -> { updateUiState { copy(isLoading = true) } }
                is Resource.Success -> { updateUiState { copy(productDetail = resource.data) } }
                is Resource.Error -> { updateUiState { copy(error = resource.message) } }
            }
        }
    }


    fun toggleFavorite() {
        val productDetail = _uiState.value.productDetail ?: return
        viewModelScope.launch {
            productDetail.id?.let { id ->
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
            val addToFavoriteBody = AddToFavoriteBody(
                userId = firebaseAuthRepository.getUserId(),
                productId = productId
            )
            addToFavoriteUseCase(addToFavoriteBody).collect { response ->
                when (response) {
                    is Resource.Loading -> { updateUiState { copy(isLoading = true) } }
                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                productDetail = productDetail?.copy(isFavorite = true),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        updateUiState { copy( error = response.message, isLoading = false) } }
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
                    is Resource.Loading -> { updateUiState { copy(isLoading = true) } }

                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                productDetail = productDetail?.copy(isFavorite = false),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> { updateUiState { copy( error = response.message, isLoading = false) } }
                }
            }
        }
    }

    private fun updateUiState(block: ProductDetailContract.UiState.() -> ProductDetailContract.UiState) {
        _uiState.update(block)
    }

}