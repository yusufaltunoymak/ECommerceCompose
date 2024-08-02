package com.hoy.ecommercecompose.data.mapper

import com.hoy.ecommercecompose.data.source.remote.model.Product
import com.hoy.ecommercecompose.domain.model.ProductUi

fun Product.mapToProductUi() : ProductUi {
    return ProductUi(
        category = this.category ?: "",
        count = this.count ?: 0,
        description = this.description ?: "",
        id = this.id ?: 0,
        imageOne = this.imageOne ?: "",
        price = this.price ?: 0.0,
        title = this.title ?: "",
        isFavorite = false,
        rate = this.rate ?: 0.0
    )
}
