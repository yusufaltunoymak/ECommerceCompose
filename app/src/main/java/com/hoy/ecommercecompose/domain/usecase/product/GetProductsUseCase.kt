package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.response.GetProductResponse
import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl
) {
    suspend operator fun invoke(): Resource<GetProductResponse> =  productRepositoryImpl.getProducts()
}