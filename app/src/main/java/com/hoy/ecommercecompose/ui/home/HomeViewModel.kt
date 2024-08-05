package com.hoy.ecommercecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import com.hoy.ecommercecompose.domain.usecase.category.GetCategoriesUseCase
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
    private val getProductsUseCase: GetProductsUseCase
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

}