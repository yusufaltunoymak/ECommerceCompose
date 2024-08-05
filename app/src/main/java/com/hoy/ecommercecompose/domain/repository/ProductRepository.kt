package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto

interface ProductRepository {

    suspend fun getProducts(): ProductListDto
    suspend fun getCategories(): GetCategoriesResponse
    suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse>
    suspend fun getCartProducts(id: String): Resource<GetCartProductResponse>
}