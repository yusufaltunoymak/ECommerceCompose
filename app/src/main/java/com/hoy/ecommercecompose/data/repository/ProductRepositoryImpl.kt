package com.hoy.ecommercecompose.data.repository

import android.util.Log
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductResponse
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProducts(): Resource<GetProductResponse> {
        return try {
            val response = apiService.getProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getCategories(): Resource<GetCategoriesResponse> {
        return try {
            val response = apiService.getCategories()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
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
}