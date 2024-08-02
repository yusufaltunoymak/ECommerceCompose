package com.hoy.ecommercecompose.ui.home

import com.hoy.ecommercecompose.data.model.User
import com.hoy.ecommercecompose.data.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.model.response.GetProductResponse
import com.hoy.ecommercecompose.domain.model.ProductUi

data class HomeUiState(
    val isLoading : Boolean? = null,
    val currentUser : User? = null,
    val errorMessage : String? = null,
    val categoryList : GetCategoriesResponse? = null,
    val productList : List<ProductUi>? = null
)