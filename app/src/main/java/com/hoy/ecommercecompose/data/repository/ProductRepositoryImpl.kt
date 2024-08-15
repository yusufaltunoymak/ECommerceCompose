package com.hoy.ecommercecompose.data.repository

import android.util.Log
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.ProductDao
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.data.source.remote.model.CheckFavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getProducts(): ProductListDto {
        return apiService.getProducts()
    }

    override suspend fun getCategories(): GetCategoriesResponse {
        return apiService.getCategories()
    }

    override suspend fun getProductDetail(id: Int): GetProductDetailResponse {
        return apiService.getProductDetail(id = id)
    }

    override suspend fun checkProductIsFavorite(userId: String,productId :Int): CheckFavoriteResponse {
        return apiService.checkIsFavorite(userId = userId, productId = productId)
    }

    override suspend fun getCartProducts(id: String):GetCartProductResponse {
        return apiService.getCartProducts(id = id)
    }

    override suspend fun addFavoriteProduct(
        baseBody: BaseBody
    ): BaseResponse {
        return apiService.addToFavorites(baseBody = baseBody)
    }

    override suspend fun getFavoriteProducts(userId: String): ProductListDto {
        return apiService.getFavorites(userId = userId)
    }

    override suspend fun deleteFavoriteProduct(deleteFromFavoriteBody: DeleteFromFavoriteBody): BaseResponse {
        return apiService.deleteFromFavorites(deleteFromFavoriteBody = deleteFromFavoriteBody)
    }

    override suspend fun addToCard(baseBody: BaseBody): BaseResponse {
        return apiService.addToCart(addToCartBody = baseBody)
    }

    override suspend fun getByCategory(category: String): ProductListDto {
        return apiService.getProductsByCategory(category = category)
    }

    override suspend fun getCartProductsLocal(userId: String): List<ProductEntity> {
        return productDao.getCartProducts()
    }

    override suspend fun addToCartProduct(product: ProductEntity) {
       return productDao.addToCartProduct(product)
    }

    override suspend fun deleteFromCartProduct(product: ProductEntity) {
        return productDao.deleteFromCartProduct(product)
    }



}