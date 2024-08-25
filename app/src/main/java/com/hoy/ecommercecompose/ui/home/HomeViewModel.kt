package com.hoy.ecommercecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import com.hoy.ecommercecompose.domain.usecase.category.GetCategoriesUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.AddToFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.DeleteFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.product.GetProductsUseCase
import com.hoy.ecommercecompose.domain.usecase.product.GetSpecialProductsUseCase
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
class HomeViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val getSpecialProductsUseCase: GetSpecialProductsUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<HomeContract.UiState> =
        MutableStateFlow(HomeContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<HomeContract.UiEffect>() }
    val uiEffect: Flow<HomeContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        getUserInformation()
        getCategories()
        getProducts()
        getSpecialProducts()
    }

    fun onAction(action: HomeContract.UiAction) {
        when (action) {
            is HomeContract.UiAction.ToggleFavoriteClick -> toggleFavorite(
                firebaseAuthRepository.getUserId(),
                action.product
            )
        }
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            when (val result = getUserInformationUseCase()) {
                is Resource.Success -> {
                    updateUiState { copy(currentUser = result.data) }
                }

                is Resource.Error -> {
                    updateUiState { copy(errorMessage = result.message) }
                }

                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = result.message, isLoading = false) }
                    }

                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateUiState { copy(categoryList = result.data, isLoading = false) }
                    }
                }
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase(firebaseAuthRepository.getUserId()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateUiState { copy(productList = result.data, isLoading = false) }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = result.message, isLoading = false) }
                    }

                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getSpecialProducts() {
        viewModelScope.launch {
            getSpecialProductsUseCase(firebaseAuthRepository.getUserId()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateUiState { copy(specialProductList = result.data, isLoading = false) }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(errorMessage = result.message, isLoading = false) }
                    }

                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun addToFavorites(userId: String, productId: Int) {
        val baseBody = BaseBody(productId, userId)
        viewModelScope.launch {
            addToFavoriteUseCase(baseBody).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateUiState {
                            val updatedProductList = productList.map { product ->
                                if (product.id == productId) {
                                    product.copy(isFavorite = true)
                                } else {
                                    product
                                }
                            }
                            val updatedSpecialProductList = specialProductList.map { product ->
                                if (product.id == productId) {
                                    product.copy(isFavorite = true)
                                } else {
                                    product
                                }
                            }
                            copy(
                                productList = updatedProductList,
                                specialProductList = updatedSpecialProductList,
                                addToFavorites = result.data,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateUiState {
                            copy(errorMessage = result.message, isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        updateUiState {
                            copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun toggleFavorite(userId: String, product: ProductUi) {
        if (product.isFavorite) {
            deleteFavorite(userId, product.id)
        } else {
            addToFavorites(userId, product.id)
        }
    }

    private fun deleteFavorite(userId: String, productId: Int) {
        val deleteFromFavoriteBody = DeleteFromFavoriteBody(userId, productId)
        viewModelScope.launch {
            deleteFavoriteUseCase(deleteFromFavoriteBody).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateUiState {
                            val updatedProductList = productList.map { product ->
                                if (product.id == productId) {
                                    product.copy(isFavorite = false)
                                } else {
                                    product
                                }
                            }
                            copy(
                                productList = updatedProductList,
                                deleteFromFavorites = result.data,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateUiState {
                            copy(errorMessage = result.message, isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        updateUiState {
                            copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun updateUiState(block: HomeContract.UiState.() -> HomeContract.UiState) {
        _uiState.update(block)
    }
}
