package com.hoy.ecommercecompose.domain.usecase.cart

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartProductsLocalUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(userId: String) : Flow<Resource<List<ProductEntity>>> {
        return productRepository.getCartProductsLocal(userId)
    }
}