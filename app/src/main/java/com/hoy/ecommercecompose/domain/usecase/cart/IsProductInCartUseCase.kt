package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class IsProductInCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Boolean {
        return productRepository.isProductInCart(productId)
    }
}