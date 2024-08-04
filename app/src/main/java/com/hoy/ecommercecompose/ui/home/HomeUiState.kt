package com.hoy.ecommercecompose.ui.home

import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.data.source.remote.model.User
import com.hoy.ecommercecompose.domain.model.ProductUi

data class HomeUiState(
    val isLoading : Boolean? = null,
    val currentUser : User? = null,
    val errorMessage : String? = null,
    val categoryList : List<Category> = emptyList(),
    val productList : List<ProductUi> = emptyList()
)