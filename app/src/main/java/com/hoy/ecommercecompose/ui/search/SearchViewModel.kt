package com.hoy.ecommercecompose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.usecase.product.GetAllProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllProductUseCase: GetAllProductUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<SearchContract.UiState> =
        MutableStateFlow(SearchContract.UiState())
    val uiState: StateFlow<SearchContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SearchContract.UiEffect>() }
    val uiEffect: Flow<SearchContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private var allProducts: List<ProductUi> = emptyList()

    fun onAction(action: SearchContract.UiAction) {
        when (action) {
            is SearchContract.UiAction.ChangeQuery -> {
                updateUiState { copy(searchQuery = action.query) }
                searchProducts(action.query)
            }

            is SearchContract.UiAction.LoadProduct -> updateUiState { copy(productList = action.productList) }
        }
    }

    fun loadAllProducts() {
        viewModelScope.launch {
            getAllProductUseCase("")
                .onStart { updateUiState { copy(isLoading = true) } }
                .onCompletion { updateUiState { copy(isLoading = false) } }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            updateUiState {
                                allProducts = result.data
                                copy(productList = allProducts)
                            }
                        }

                        is Resource.Error -> {
                            updateUiState { copy(errorMessage = result.message) }
                        }
                    }
                }
        }
    }

    private fun searchProducts(query: String) {
        val filteredList = if (query.isEmpty()) {
            allProducts
        } else {
            allProducts.filter { it.title.contains(query, ignoreCase = true) }
        }
        updateUiState { copy(productList = filteredList) }
    }

    private fun updateUiState(block: SearchContract.UiState.() -> SearchContract.UiState) {
        _uiState.update(block)
    }
}
