package com.hoy.ecommercecompose.domain.usecase.payment

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.payment.OrderedProductEntity
import com.hoy.ecommercecompose.data.source.local.payment.PaymentEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddPaymentUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        payment: PaymentEntity,
        orderedProducts: List<OrderedProductEntity>,
        userId: String
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading)
            try {
                productRepository.processOrder(payment, orderedProducts, userId)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }
}
