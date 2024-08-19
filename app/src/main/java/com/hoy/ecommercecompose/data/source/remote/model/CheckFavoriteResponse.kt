package com.hoy.ecommercecompose.data.source.remote.model

import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse

data class CheckFavoriteResponse(
    val isFavorite: Boolean
) : BaseResponse()
