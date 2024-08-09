package com.hoy.ecommercecompose.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.usecase.product.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val savedStateHandle: SavedStateHandle,

    ) : ViewModel() {
    private var _detailUiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())

    val detailUiState = _detailUiState.asStateFlow()

    init {
        savedStateHandle.get<Int>("productId")?.let {
            getProductDetail(it)
        }
    }

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            getProductDetailUseCase(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _detailUiState.value = DetailUiState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _detailUiState.value = DetailUiState(productDetail = resource.data)
                    }

                    is Resource.Error -> {
                        _detailUiState.value = DetailUiState(error = resource.message)
                    }
                }
            }
        }
    }
}