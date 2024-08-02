package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(id: String) = productRepository.getCartProducts(id)
}