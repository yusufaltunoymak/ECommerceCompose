package com.hoy.ecommercecompose.data.source.remote.model.response

import com.hoy.ecommercecompose.data.source.remote.model.ProductDto

data class GetProductDetailResponse(
    val productDto: ProductDto?
) : BaseResponse()
