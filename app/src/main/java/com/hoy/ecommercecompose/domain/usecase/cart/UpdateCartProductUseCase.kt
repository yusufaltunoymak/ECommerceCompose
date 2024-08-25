package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateCartProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: ProductEntity) {
        productRepository.updateCartProduct(product)
    }
}
