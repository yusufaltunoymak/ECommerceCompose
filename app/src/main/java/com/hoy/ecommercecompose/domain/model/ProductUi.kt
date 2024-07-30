package com.hoy.ecommercecompose.domain.model

data class ProductUi(
    val category: String,
    val count: Int,
    val description: String,
    val id: Int,
    val imageOne: String,
    val imageThree: String,
    val imageTwo: String,
    val price: Int,
    val rate: Int,
    val salePrice: Int,
    val saleState: Boolean,
    val title: String,
    val isFavorite: Boolean
)
