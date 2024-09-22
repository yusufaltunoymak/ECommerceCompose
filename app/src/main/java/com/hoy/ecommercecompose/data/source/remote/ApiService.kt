package com.hoy.ecommercecompose.data.source.remote

import com.hoy.ecommercecompose.BuildConfig
import com.hoy.ecommercecompose.data.source.remote.model.CheckFavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCartProductResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("get_products")
    suspend fun getProducts(
        @Header("store") store: String = BuildConfig.USER
    ): ProductListDto

    @GET("get_categories")
    suspend fun getCategories(
        @Header("store") store: String = BuildConfig.USER
    ): GetCategoriesResponse

    @GET("get_product_detail")
    suspend fun getProductDetail(
        @Header("store") store: String = BuildConfig.USER,
        @Query("id") id: Int
    ): GetProductDetailResponse

    @GET("check_favorite")
    suspend fun checkIsFavorite(
        @Header("store") store: String = BuildConfig.USER,
        @Query("userId") userId: String,
        @Query("productId") productId: Int
    ): CheckFavoriteResponse

    @GET("get_cart_products")
    suspend fun getCartProducts(
        @Header("store") store: String = BuildConfig.USER,
        @Query("userId") id: String
    ): GetCartProductResponse

    @POST("add_to_favorites")
    suspend fun addToFavorites(
        @Header("store") store: String = BuildConfig.USER,
        @Body baseBody: BaseBody
    ): BaseResponse

    @GET("get_favorites")
    suspend fun getFavorites(
        @Header("store") store: String = BuildConfig.USER,
        @Query("userId") userId: String
    ): ProductListDto

    @POST("delete_from_favorites")
    suspend fun deleteFromFavorites(
        @Header("store") store: String = BuildConfig.USER,
        @Body deleteFromFavoriteBody: DeleteFromFavoriteBody
    ): BaseResponse

    @GET("get_products_by_category")
    suspend fun getProductsByCategory(
        @Header("store") store: String = BuildConfig.USER,
        @Query("category") category: String
    ): ProductListDto
}
