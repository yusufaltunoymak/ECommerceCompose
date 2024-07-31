package com.hoy.ecommercecompose.data.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.model.response.GetProductResponse
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProducts(): Resource<GetProductResponse> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Error: Empty response body")
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getCategories() : Resource<GetCategoriesResponse> {
        return try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Error: Empty response body")
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse> {
        return try {
            val response = apiService.getProductDetail(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Error: Empty response body")
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }
}