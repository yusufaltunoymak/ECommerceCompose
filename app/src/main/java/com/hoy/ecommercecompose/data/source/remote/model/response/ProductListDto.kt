package com.hoy.ecommercecompose.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto

data class ProductListDto(
    @SerializedName("products")
    val productDto: List<ProductDto>
) : BaseResponse()
