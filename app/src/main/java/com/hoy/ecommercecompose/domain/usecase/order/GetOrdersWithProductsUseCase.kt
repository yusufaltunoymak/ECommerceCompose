package com.hoy.ecommercecompose.domain.usecase.order

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.payment.PaymentWithProducts
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrdersWithProductsUseCase @Inject constructor(
    private val orderRepository: ProductRepository
) {
    operator fun invoke(userId: String): Flow<Resource<List<PaymentWithProducts>>> = flow {
        try {
            emit(Resource.Loading)
            val orders = orderRepository.getOrdersWithProducts(userId).first()
            emit(Resource.Success(orders))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
