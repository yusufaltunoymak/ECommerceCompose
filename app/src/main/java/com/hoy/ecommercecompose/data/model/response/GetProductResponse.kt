package com.hoy.ecommercecompose.data.model.response

import com.hoy.ecommercecompose.data.model.Product

data class GetProductResponse(
    val products: List<Product>?
):BaseResponse()