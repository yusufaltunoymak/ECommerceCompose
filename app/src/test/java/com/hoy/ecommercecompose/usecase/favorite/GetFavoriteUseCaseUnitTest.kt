package com.hoy.ecommercecompose.usecase.favorite

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.favorite.GetFavoriteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetFavoriteUseCaseUnitTest {
    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getFavoriteUseCase = GetFavoriteUseCase(productRepository)
    }

    @Test
    fun `test getFavoriteUseCase returns success`() = runBlocking {
        val favoriteProducts = listOf(
            ProductDto(
                id = 1,
                title = "Product 1",
                category = "Electronics",
                count = 50,
                description = "A high-quality smartphone with a sleek design.",
                imageOne = "",
                imageTwo = "",
                imageThree = "",
                price = 999.99,
                rate = 4.5,
                salePrice = 899.99,
                saleState = true
            ),
            ProductDto(
                id = 2,
                title = "Product 2",
                category = "Home Appliances",
                count = 20,
                description = "A powerful and energy-efficient vacuum cleaner.",
                imageOne = "",
                imageTwo = "",
                imageThree = "",
                price = 299.99,
                rate = 4.7,
                salePrice = 279.99,
                saleState = true
            )
        )

        val expectedResponse = ProductListDto(favoriteProducts)
        `when`(productRepository.getFavoriteProducts("userId")).thenReturn(expectedResponse)
        getFavoriteUseCase("userId").collect { result ->
            assert(result is Resource.Success)
            val data = (result as Resource.Success).data
            assert(data.size == 2)
        }
    }

    @Test
    fun `test getFavoriteUseCase handles exception`() = runBlocking {
        `when`(productRepository.getFavoriteProducts("userId")).thenThrow(RuntimeException("API error"))
        getFavoriteUseCase("userId").collect { result ->
            assert(result is Resource.Error)
            assert((result as Resource.Error).message == "API error")
        }
    }
}