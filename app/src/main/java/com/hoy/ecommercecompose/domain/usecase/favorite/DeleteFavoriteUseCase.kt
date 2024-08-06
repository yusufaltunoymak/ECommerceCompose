package com.hoy.ecommercecompose.domain.usecase.favorite

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.toFavoriteResponse
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(deleteFromFavoriteBody: DeleteFromFavoriteBody) : Flow<Resource<FavoriteResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = productRepository.deleteFavoriteProduct(deleteFromFavoriteBody)
                emit(Resource.Success(data = response.toFavoriteResponse()))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}