package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.mapToProductUi
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
private const val MINIMUM_ACCEPTABLE_RATE = 4.0

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<List<ProductUi>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = productRepository.getProducts()

                val favoriteResponse = productRepository.getFavoriteProducts(userId = userId)
                val favoriteProductIds = favoriteResponse.productDto.map { it.id }

                val filteredList = response.productDto.filter { product ->
                    product.rate?.let { rate ->
                        rate > MINIMUM_ACCEPTABLE_RATE
                    } == true
                }

                val updatedProductList = filteredList.map { productDto ->
                    val isFavorite = favoriteProductIds.contains(productDto.id)
                    productDto.mapToProductUi().copy(isFavorite = isFavorite)
                }
                emit(Resource.Success(data = updatedProductList))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}
