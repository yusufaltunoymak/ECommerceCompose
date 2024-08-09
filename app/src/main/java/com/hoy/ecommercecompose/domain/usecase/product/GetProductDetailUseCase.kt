package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductDetail
import com.hoy.ecommercecompose.domain.model.ProductDetail
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Flow<Resource<ProductDetail>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = productRepository.getProductDetail(productId)
                if (response.productDto != null) {
                    emit(Resource.Success(data = response.productDto.mapToProductDetail()))
                } else {
                    emit(Resource.Error(message = "Product not found"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "Unknown error"))
            }
        }
    }
}