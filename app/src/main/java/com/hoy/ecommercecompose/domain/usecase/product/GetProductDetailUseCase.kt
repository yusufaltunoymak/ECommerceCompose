package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl

class GetProductDetailUseCase (
    private val productRepositoryImpl: ProductRepositoryImpl
) {
    suspend operator fun invoke(productId: Int) = productRepositoryImpl.getProductDetail(productId)
}