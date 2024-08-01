package com.hoy.ecommercecompose.ui.home

import com.hoy.ecommercecompose.data.model.User

data class HomeUiState(
    val isLoading : Boolean? = null,
    val currentUser : User? = null,
    val errorMessage : String? = null
)