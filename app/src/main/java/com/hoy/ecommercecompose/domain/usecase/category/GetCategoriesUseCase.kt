package com.hoy.ecommercecompose.domain.usecase.category

import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl
) {
    suspend operator fun invoke() = productRepositoryImpl.getCategories()
}