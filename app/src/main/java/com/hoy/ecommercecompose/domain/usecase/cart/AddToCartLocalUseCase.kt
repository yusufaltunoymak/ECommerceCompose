package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartLocalUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(entity: ProductEntity): Flow<Resource<Unit>> {
        return flow {
            try {
                productRepository.addToCartProduct(entity)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred!"))
            }
        }
    }
}
