package com.hoy.ecommercecompose.data.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.AddToFavoriteBody
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProducts(): ProductListDto {
        return apiService.getProducts()
    }

    override suspend fun getCategories(): GetCategoriesResponse {
       return apiService.getCategories()
    }

    override suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse> {
        return try {
            val response = apiService.getProductDetail(id = id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
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
}