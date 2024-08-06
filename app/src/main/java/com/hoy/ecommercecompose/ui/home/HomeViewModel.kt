package com.hoy.ecommercecompose.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.AddToFavoriteBody
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import com.hoy.ecommercecompose.domain.usecase.category.GetCategoriesUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.AddToFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.GetFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserInformation()
        getCategories()
        getProducts()
        loadFavorites()
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            _uiState.update { uiState ->
                uiState.copy(
                    isLoading = true
                )
            }

            when (val result = getUserInformationUseCase()) {
                is Resource.Success -> {
                    _uiState.update { uiState ->
                        uiState.copy(
                            currentUser = result.data,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { uiState ->
                        uiState.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { categoryList ->
                            _uiState.update { uiState ->
                                uiState.copy(
                                    categoryList = categoryList,
                                    isLoading = false
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
            _uiState.update { uiState ->
                uiState.copy(
                    isLoading = true
                )
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { productList ->
                            _uiState.update { uiState ->
                                uiState.copy(
                                    productList = productList,
                                    isLoading = false
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun addToFavorites(userId: String, productId: Int) {
        val addToFavoriteBody = AddToFavoriteBody(productId, userId)
        Log.d("HomeViewModel", "Adding to favorites: userId = $userId, productId = $productId")
        viewModelScope.launch {
            addToFavoriteUseCase(addToFavoriteBody).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { favorite ->
                            _uiState.update { uiState ->
                                val updatedProductList = uiState.productList.map { product ->
                                    if (product.id == productId) {
                                        product.copy(isFavorite = true) // Veya toggle etmek isterseniz: !product.isFavorite
                                    } else {
                                        product
                                    }
                                }
                                uiState.copy(
                                    productList = updatedProductList,
                                    addToFavorites = favorite,
                                    isLoading = false
                                )
                            }
                        }
                        Log.e("HomeViewModel", "addToFavorites: ${result.data}")
                    }

                    is Resource.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadFavorites() {
        val userId = uiState.value.currentUser?.id ?: return
        viewModelScope.launch {
            getFavoriteUseCase(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { favoriteProducts ->
                            _uiState.update { uiState ->
                                val updatedProductList = uiState.productList.map { product ->
                                    if (favoriteProducts.any { it.id == product.id }) {
                                        product.copy(isFavorite = true)
                                    } else {
                                        product
                                    }
                                }
                                uiState.copy(
                                    productList = updatedProductList
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }


}