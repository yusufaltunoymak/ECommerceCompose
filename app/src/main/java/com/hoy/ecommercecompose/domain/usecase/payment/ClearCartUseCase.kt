package com.hoy.ecommercecompose.domain.usecase.payment

import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(userId: String) = productRepository.clearCart(userId)
}
