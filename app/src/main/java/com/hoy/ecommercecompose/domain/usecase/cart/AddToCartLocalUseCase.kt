package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class AddToCartLocalUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(entity: ProductEntity) = productRepository.addToCartProduct(entity)
}