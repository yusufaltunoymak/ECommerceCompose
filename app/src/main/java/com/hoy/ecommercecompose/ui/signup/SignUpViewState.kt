package com.hoy.ecommercecompose.ui.signup

data class SignUpViewState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null
)