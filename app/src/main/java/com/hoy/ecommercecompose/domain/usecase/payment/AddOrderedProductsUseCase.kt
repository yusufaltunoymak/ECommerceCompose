package com.hoy.ecommercecompose.domain.usecase.payment

import com.hoy.ecommercecompose.data.source.local.payment.OrderedProductEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class AddOrderedProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(orderedProducts: List<OrderedProductEntity>) {
        productRepository.addOrderedProducts(orderedProducts)
    }
}
