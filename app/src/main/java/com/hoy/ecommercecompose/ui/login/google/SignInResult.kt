package com.hoy.ecommercecompose.ui.login.google

import com.hoy.ecommercecompose.data.model.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)
