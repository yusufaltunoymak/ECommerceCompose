package com.hoy.ecommercecompose.data.mapper

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
        rate = this.rate ?: 0.0
    )
}

fun BaseResponse.toFavoriteResponse(): FavoriteResponse {
    return FavoriteResponse(
        message = message ?: "",
        status = status ?: -1
    )
}
