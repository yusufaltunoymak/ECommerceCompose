package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductUi
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<ProductUi>>> {
        return flow {
            try {
                val response = productRepository.getProducts()
                val allProducts = response.productDto.map { it.mapToProductUi() }

                val filteredProducts = allProducts.filter {
                    it.title.contains(query, ignoreCase = true)
                }
                emit(Resource.Success(data = filteredProducts))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}
