package com.hoy.ecommercecompose.data.model.response

import com.hoy.ecommercecompose.data.model.Product

data class GetProductDetailResponse(
    val product: Product?
) : BaseResponse()
