package com.hoy.ecommercecompose.data.source.remote.model.response

import com.hoy.ecommercecompose.data.source.remote.model.ProductDto

data class GetCartProductResponse(
    val productDtos: List<ProductDto>?
): BaseResponse()