package com.hoy.ecommercecompose.data.mapper

import com.hoy.ecommercecompose.common.orEmpty
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail
import com.hoy.ecommercecompose.domain.model.ProductUi

fun ProductDto.mapToProductUi(): ProductUi {
    return ProductUi(
        category = this.category.orEmpty(),
        count = this.count ?: 0,
        description = this.description.orEmpty(),
        id = this.id.orEmpty(),
        imageOne = this.imageOne.orEmpty(),
        price = this.price ?: 0.0,
        title = this.title.orEmpty(),
        isFavorite = false,
        rate = this.rate ?: 0.0,
        salePrice = this.salePrice ?: 0.0,
        saleState = this.saleState ?: false,
        imageThree = this.imageThree.orEmpty(),
        imageTwo = this.imageTwo.orEmpty()
    )
}


fun ProductDto.mapToProductDetail(isFavorite: Boolean): ProductDetail {
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

fun ProductDetail.mapToProductEntity(
    userId: String,
    productId: Int,
    category: String,
    count: Int,
    quantity: Int
): ProductEntity {
    return ProductEntity(
        id = userId,
        productId = productId,
        category = category,
        count = count,
        description = this.description.orEmpty(),
        imageOne = this.imageOne.orEmpty(),
        imageThree = this.imageThree.orEmpty(),
        imageTwo = this.imageTwo.orEmpty(),
        price = this.price ?: 0.0,
        rate = this.rate ?: 0.0,
        salePrice = this.salePrice ?: 0.0,
        saleState = this.saleState ?: false,
        title = this.title.orEmpty(),
        isFavorite = this.isFavorite ?: false,
        quantity = quantity
    )
}

fun BaseResponse.toFavoriteResponse(): FavoriteResponse {
    return FavoriteResponse(
        message = message.orEmpty(),
        status = status ?: -1
    )
}
