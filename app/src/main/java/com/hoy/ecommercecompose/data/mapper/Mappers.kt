package com.hoy.ecommercecompose.data.mapper

import com.hoy.ecommercecompose.common.orEmpty
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail
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

fun ProductDto.mapToProductDetail(isFavorite : Boolean): ProductDetail {
    return ProductDetail(
        description = this.description.orEmpty(),
        id = this.id.orEmpty(),
        imageOne = this.imageOne.orEmpty(),
        price = this.price.orEmpty(),
        title = this.title.orEmpty(),
        isFavorite = isFavorite,
        rate = this.rate.orEmpty(),
        salePrice = this.salePrice.orEmpty(),
        saleState = this.saleState.orEmpty(),
        imageThree = this.imageThree.orEmpty(),
        imageTwo = this.imageTwo.orEmpty(),
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
