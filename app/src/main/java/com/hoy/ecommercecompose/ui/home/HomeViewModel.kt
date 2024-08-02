package com.hoy.ecommercecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductUi
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.domain.usecase.auth.GetUserInformationUseCase
import com.hoy.ecommercecompose.domain.usecase.category.GetCategoriesUseCase
import com.hoy.ecommercecompose.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (val result = getUserInformationUseCase()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        currentUser = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (val result = getCategoriesUseCase()) {
                is Resource.Success -> {
                    val uniqueCategories =
                        result.data?.categories?.distinctBy { category -> category.name }
                    _uiState.value = _uiState.value.copy(
                        categoryList = uniqueCategories?.let {
                            GetCategoriesResponse(
                                categories = it
                            )
                        },
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (val result = getProductsUseCase()) {
                is Resource.Success -> {
                    val filteredList = result.data?.products?.filter { product ->
                        product.rate!! > 4.8
                    }
                    val mappedList = filteredList?.map { product ->
                        product.mapToProductUi()
                    }
                    _uiState.value = _uiState.value.copy(
                        productList = mappedList,
                        isLoading = false
                    )

                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }
            }
        }
    }
}