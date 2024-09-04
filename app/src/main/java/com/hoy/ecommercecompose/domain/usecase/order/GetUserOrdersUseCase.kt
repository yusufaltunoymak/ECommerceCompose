package com.hoy.ecommercecompose.domain.usecase.order

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserOrdersUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(userId: String): Flow<Resource<List<PaymentEntity>>> {
        return flow {
            try {
                val orders = productRepository.getUserOrders(userId).first()
                emit(Resource.Success(orders))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
