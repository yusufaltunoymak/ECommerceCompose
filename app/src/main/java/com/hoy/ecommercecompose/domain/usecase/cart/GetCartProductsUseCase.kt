package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductUi
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<List<ProductUi>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = productRepository.getCartProducts(id)
                response.productDto?.let { productDto ->
                    val allProducts = productDto.map { it.mapToProductUi() }
                    emit(Resource.Success(data = allProducts))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}