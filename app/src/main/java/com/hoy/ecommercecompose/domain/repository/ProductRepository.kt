package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.model.response.GetProductResponse

interface ProductRepository {

    suspend fun getProducts(): Resource<GetProductResponse>
    suspend fun getCategories(): Resource<GetCategoriesResponse>
    suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse>

}