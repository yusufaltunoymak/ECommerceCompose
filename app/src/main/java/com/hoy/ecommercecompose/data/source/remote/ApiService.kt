package com.hoy.ecommercecompose.data.source.remote

import com.hoy.ecommercecompose.common.Constants.USER
import com.hoy.ecommercecompose.data.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.model.response.GetProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("get_products")
    suspend fun getProducts(
        @Header("store") store: String = USER
    ): Response<GetProductResponse>

    @GET("get_categories")
    suspend fun getCategories(
        @Header("store") store: String = USER
    ): Response<GetCategoriesResponse>

}