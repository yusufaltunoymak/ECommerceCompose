package com.hoy.ecommercecompose.data.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.model.response.GetProductResponse
import com.hoy.ecommercecompose.data.source.remote.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProducts() : Resource<GetProductResponse> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    suspend fun getCategories() : Resource<GetCategoriesResponse> {
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
}