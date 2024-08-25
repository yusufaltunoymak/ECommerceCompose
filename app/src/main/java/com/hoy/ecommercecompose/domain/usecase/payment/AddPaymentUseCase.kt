package com.hoy.ecommercecompose.domain.usecase.payment

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddPaymentUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(paymentEntity: PaymentEntity): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading)
            try {
                productRepository.processOrder(paymentEntity.userId, paymentEntity)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
