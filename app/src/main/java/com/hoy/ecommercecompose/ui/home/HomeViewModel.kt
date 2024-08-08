package com.hoy.ecommercecompose.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.AddToFavoriteBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import com.hoy.ecommercecompose.domain.usecase.category.GetCategoriesUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.AddToFavoriteUseCase
import com.hoy.ecommercecompose.domain.usecase.favorite.DeleteFavoriteUseCase
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
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserInformation()
        getCategories()
        getProducts()
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

     fun getProducts() {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            getProductsUseCase(userId).collect { result ->
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

    private fun addToFavorites(userId: String, productId: Int) {
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

    fun toggleFavorite(userId: String, product: ProductUi) {
        if (product.isFavorite) {
            deleteFavorite(userId, product.id)
        } else {
            addToFavorites(userId, product.id)
        }
    }

    private fun deleteFavorite(userId: String, productId: Int) {
        val deleteFromFavoriteBody = DeleteFromFavoriteBody(userId, productId)
        Log.d("HomeViewModel", "Deleting from favorites: userId = $userId, productId = $productId")
        viewModelScope.launch {
            deleteFavoriteUseCase(deleteFromFavoriteBody).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { favorite ->
                            _uiState.update { uiState ->
                                val updatedProductList = uiState.productList.map { product ->
                                    if (product.id == productId) {
                                        product.copy(isFavorite = false)
                                    } else {
                                        product
                                    }
                                }
                                uiState.copy(
                                    productList = updatedProductList,
                                    deleteFromFavorites = favorite,
                                    isLoading = false
                                )
                            }
                        }
                        Log.e("HomeViewModel", "deleteFavorite: ${result.data}")
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