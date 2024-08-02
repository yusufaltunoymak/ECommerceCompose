package com.hoy.ecommercecompose.data.source.remote

import com.hoy.ecommercecompose.common.Constants.USER
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("get_products")
    suspend fun getProducts(
        @Header("store") store: String = USER
    ): GetProductResponse

    @GET("get_categories")
    suspend fun getCategories(
        @Header("store") store: String = USER
    ): GetCategoriesResponse

    @GET("get_product_detail")
    suspend fun getProductDetail(
        @Header("store") store: String = USER,
        @Query("id") id: Int
    ): GetProductDetailResponse

    @GET("get_cart_products")
    suspend fun getCartProducts(
        @Header("store") store: String = USER,
        @Query("user_id") id: String
    ): GetCartProductResponse

}