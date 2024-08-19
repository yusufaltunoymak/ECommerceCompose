package com.hoy.ecommercecompose.data.source.remote.model.response

import com.hoy.ecommercecompose.data.source.remote.model.Category

data class GetCategoriesResponse(
    val categories: List<Category>
) : BaseResponse()
