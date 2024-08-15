package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class GetCartProductsLocalUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(userId: String) = productRepository.getCartProductsLocal(userId)
}