package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(baseBody: BaseBody) : Flow<Resource<BaseResponse>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = repository.addToCard(baseBody)
                emit(Resource.Success(data = response))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}