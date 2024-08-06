package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.AddToFavoriteBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody

interface ProductRepository {

    suspend fun getProducts(): ProductListDto
    suspend fun getCategories(): GetCategoriesResponse
    suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse>
    suspend fun getCartProducts(id: String): Resource<GetCartProductResponse>

    suspend fun getFavoriteProducts(): List<ProductEntity>
    suspend fun addFavoriteProduct(product: ProductEntity)
    suspend fun removeFavoriteProduct(product: ProductEntity)

    suspend fun addFavoriteProduct(addToFavoriteBody: AddToFavoriteBody): BaseResponse
    suspend fun getFavoriteProducts(userId: String): ProductListDto
    suspend fun deleteFavoriteProduct(deleteFromFavoriteBody: DeleteFromFavoriteBody): BaseResponse
}