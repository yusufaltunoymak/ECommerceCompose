package com.hoy.ecommercecompose.data.model.response

import com.hoy.ecommercecompose.data.model.Category

data class GetCategoriesResponse(
    val categories: List<Category>
) : BaseResponse()