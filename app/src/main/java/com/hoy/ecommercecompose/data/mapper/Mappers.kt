package com.hoy.ecommercecompose.data.mapper

import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.domain.model.ProductUi

fun ProductDto.mapToProductUi() : ProductUi {
    return ProductUi(
        category = this.category ?: "",
        count = this.count ?: 0,
        description = this.description ?: "",
        id = this.id ?: 0,
        imageOne = this.imageOne ?: "",
        price = this.price ?: 0.0,
        title = this.title ?: "",
        isFavorite = false,
        rate = this.rate ?: 0.0,
        salePrice = this.salePrice ?: 0.0,
        saleState = this.saleState ?: false,
        imageThree = this.imageThree ?: "",
        imageTwo = this.imageTwo ?: ""
    )
}

fun ProductEntity.mapToProductEntity() : ProductUi {
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
        isFavorite = this.isFavorite
    )
}






fun BaseResponse.toFavoriteResponse(): FavoriteResponse {
    return FavoriteResponse(
        message = message ?: "",
        status = status ?: -1
    )
}
