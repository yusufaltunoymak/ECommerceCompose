package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductDetail
import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(userId: String, productId: Int): Resource<ProductDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val response = async {
                    productRepository.getProductDetail(productId)
                }.await()
                val favResponse = async {
                    productRepository.checkProductIsFavorite(userId, productId)
                }.await()
                if (response.productDto != null) {
                    Resource.Success(data = response.productDto.mapToProductDetail(favResponse.isFavorite))
                }
                else {
                    Resource.Error(message = "Product not found")
                }
            } catch (e: Exception) {
                Resource.Error(message = e.message ?: "An unexpected error occurred")
            }
        }
    }
}