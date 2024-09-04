package com.hoy.ecommercecompose.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.product.GetSpecialProductsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetSpecialProductsUseCaseTest {

    @Mock
    lateinit var mockProductRepository: ProductRepository

    private lateinit var getSpecialProductsUseCase: GetSpecialProductsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getSpecialProductsUseCase = GetSpecialProductsUseCase(mockProductRepository)
    }

    @Test
    fun `test getSpecialProductsUseCase returns success`() = runBlocking {
        val productDtoList = listOf(
            ProductDto(
                id = 1,
                title = "Product 1",
                category = "Category 1",
                count = 10,
                description = "Description 1",
                imageOne = "",
                imageTwo = "",
                imageThree = "",
                price = 99.99,
                rate = 4.5,
                salePrice = 89.99,
                saleState = true
            ),
            ProductDto(
                id = 2,
                title = "Product 2",
                category = "Category 2",
                count = 20,
                description = "Description 2",
                imageOne = "",
                imageTwo = "",
                imageThree = "",
                price = 199.99,
                rate = 4.0,
                salePrice = 179.99,
                saleState = true
            )
        )
        val expectedResponse = ProductListDto(productDtoList)

        val favoriteProductList = listOf(
            ProductDto(
                id = 1,
                title = "Product 1",
                category = "Category 1",
                count = 10,
                description = "Description 1",
                imageOne = "",
                imageTwo = "",
                imageThree = "",
                price = 99.99,
                rate = 4.5,
                salePrice = 89.99,
                saleState = true
            )
        )
        val expectedFavoriteResponse = ProductListDto(favoriteProductList)

        `when`(mockProductRepository.getProducts()).thenReturn(expectedResponse)
        `when`(mockProductRepository.getFavoriteProducts(anyString())).thenReturn(
            expectedFavoriteResponse
        )

        getSpecialProductsUseCase("userId").collect { result ->
            assert(result is Resource.Success)
            val data = (result as Resource.Success).data

            val favoriteProduct = data.find { it.id == 1 }
            assertTrue(favoriteProduct?.isFavorite == true)
        }
    }

    @Test
    fun `test getSpecialProductsUseCase handles exception`() = runBlocking {
        `when`(mockProductRepository.getProducts()).thenThrow(RuntimeException("API error"))
        getSpecialProductsUseCase("userId").collect { result ->
            assert(result is Resource.Error)
            assertEquals("API error", (result as Resource.Error).message)
        }
    }
}
