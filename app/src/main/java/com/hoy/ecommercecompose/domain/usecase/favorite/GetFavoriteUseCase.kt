package com.hoy.ecommercecompose.domain.usecase.favorite

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductUi
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<List<ProductUi>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = productRepository.getFavoriteProducts(userId)
                emit(Resource.Success(data = response.productDto.map { it.mapToProductUi() }))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}