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
            println("ürün ${it}")
            getProductDetail(it.toInt())
        }
    }

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            getProductDetailUseCase(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        println("alo")

                        _detailUiState.value = DetailUiState(isLoading = true)
                    }

                    is Resource.Success -> {
                        println("aloo")
                        println("gelen data ${resource.data}")
                        _detailUiState.value = DetailUiState(productDetail = resource.data)
                    }

                    is Resource.Error -> {
                        println("alooo")

                        _detailUiState.value = DetailUiState(error = resource.message)
                    }
                }
            }
        }
    }
}