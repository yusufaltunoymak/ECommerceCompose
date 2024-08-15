package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.model.CheckFavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): ProductListDto
    suspend fun getCategories(): GetCategoriesResponse
    suspend fun getProductDetail(id: Int): GetProductDetailResponse
    suspend fun checkProductIsFavorite(userId: String,productId :Int): CheckFavoriteResponse
    suspend fun getCartProducts(id: String): GetCartProductResponse


    suspend fun getByCategory(category: String): ProductListDto

    suspend fun addFavoriteProduct(baseBody: BaseBody): BaseResponse
    suspend fun getFavoriteProducts(userId: String): ProductListDto
    suspend fun deleteFavoriteProduct(deleteFromFavoriteBody: DeleteFromFavoriteBody): BaseResponse

    suspend fun addToCartProduct(product: ProductEntity)
    suspend fun updateCartProduct(product: ProductEntity)
    fun getCartProductsLocal(userId: String):Flow<Resource<List<ProductEntity>>>
    suspend fun deleteFromCartProduct(productId: Int)
}