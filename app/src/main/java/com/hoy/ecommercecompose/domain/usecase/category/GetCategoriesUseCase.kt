package com.hoy.ecommercecompose.domain.usecase.category

import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() = productRepository.getCategories()
}