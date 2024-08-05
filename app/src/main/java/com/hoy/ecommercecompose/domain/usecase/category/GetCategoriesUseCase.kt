package com.hoy.ecommercecompose.domain.usecase.category

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = productRepository.getCategories()
                val distinctCategories = response.categories.distinctBy { category ->
                    category.name
                }
                emit(Resource.Success(data = distinctCategories))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}