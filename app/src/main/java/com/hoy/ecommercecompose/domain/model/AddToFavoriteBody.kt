package com.hoy.ecommercecompose.domain.model

data class AddToFavoriteBody(
    val productId: Int,
    val userId: String
)