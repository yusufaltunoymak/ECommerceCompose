package com.hoy.ecommercecompose.data.mapper

import com.hoy.ecommercecompose.data.model.Product
import com.hoy.ecommercecompose.domain.model.ProductUi

fun Product.mapToProductUi() : ProductUi {
    return ProductUi(
        category = this.category,
        count = this.count,
        description = this.description,
        id = this.id,
        imageOne = this.imageOne,
        imageThree = this.imageThree,
        imageTwo = this.imageTwo,
        price = this.price,
        rate = this.rate,
        salePrice = this.salePrice,
        saleState = this.saleState,
        title = this.title,
        isFavorite = false
    )
}
