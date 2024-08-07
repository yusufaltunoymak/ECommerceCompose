package com.hoy.ecommercecompose.ui.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(

) : ViewModel() {
    private var _detailUiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()
}