package com.hoy.ecommercecompose.data.source.remote.model.response

import com.hoy.ecommercecompose.data.source.remote.model.Product

data class GetProductDetailResponse(
    val product: Product?
) : BaseResponse()
