package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductUi
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<ProductUi>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = productRepository.getProducts()

                val filteredList = response.productDto.filter { product ->
                    product.rate?.let { rate ->
                        rate > 4.0
                    } == true
                }
                emit(Resource.Success(data = filteredList.map { it.mapToProductUi() }))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}
