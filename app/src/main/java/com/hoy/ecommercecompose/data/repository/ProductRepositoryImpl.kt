package com.hoy.ecommercecompose.data.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.ProductDao
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.AddToFavoriteBody
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

    override suspend fun getCartProducts(id: String): Resource<GetCartProductResponse> {
        return try {
            val response = apiService.getCartProducts(id = id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun addFavoriteProduct(
        addToFavoriteBody: AddToFavoriteBody
    ): BaseResponse {
        return apiService.addToFavorites(addToFavoriteBody = addToFavoriteBody)
    }

    override suspend fun getFavoriteProducts(userId: String): ProductListDto {
        return apiService.getFavorites(userId = userId)
    }

    override suspend fun deleteFavoriteProduct(deleteFromFavoriteBody: DeleteFromFavoriteBody): BaseResponse {
        return apiService.deleteFromFavorites(deleteFromFavoriteBody = deleteFromFavoriteBody)
    }

    override suspend fun getByCategory(category: String): ProductListDto {
        return apiService.getProductsByCategory(category = category)
    }


    override suspend fun addFavoriteProduct(product: ProductEntity) {
        productDao.addFavoriteProduct(product)
    }

    override suspend fun removeFavoriteProduct(product: ProductEntity) {
        productDao.removeFavoriteProduct(product)
    }

    override suspend fun getFavoriteProducts(): List<ProductEntity> {
        return productDao.getFavoriteProducts()
    }

}