package com.hoy.ecommercecompose.domain.usecase.favorite

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.mapper.toFavoriteResponse
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.FavoriteResponse
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val favoriteRepository: ProductRepository
) {
    suspend operator fun invoke(baseBody: BaseBody): Flow<Resource<FavoriteResponse>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = favoriteRepository.addFavoriteProduct(baseBody)
                emit(Resource.Success(data = response.toFavoriteResponse()))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}