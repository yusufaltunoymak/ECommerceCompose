package com.hoy.ecommercecompose.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.product.GetProductsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetProductsUseCaseTest {

    @Mock
    lateinit var mockProductRepository: ProductRepository

    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getProductsUseCase = GetProductsUseCase(mockProductRepository)
    }

    @Test
    fun `test getProductsUseCase returns filtered products with favorites marked`() = runBlocking {
        val productDtoList = listOf(
            ProductDto(
                category = "Electronics",
                count = 50,
                description = "A high-quality smartphone with a sleek design.",
                id = 1,
                imageOne = "https://example.com/images/product1_img1.jpg",
                imageTwo = "https://example.com/images/product1_img2.jpg",
                imageThree = "https://example.com/images/product1_img3.jpg",
                price = 999.99,
                rate = 4.6,
                salePrice = 899.99,
                saleState = true,
                title = "Smartphone Model X"
            ),
            ProductDto(
                category = "Home Appliances",
                count = 20,
                description = "A powerful and energy-efficient vacuum cleaner.",
                id = 2,
                imageOne = "https://example.com/images/product2_img1.jpg",
                imageTwo = "https://example.com/images/product2_img2.jpg",
                imageThree = "https://example.com/images/product2_img3.jpg",
                price = 299.99,
                rate = 3.0,
                salePrice = 279.99,
                saleState = true,
                title = "Vacuum Cleaner 2000"
            )
        )
        val expectedResponse = ProductListDto(productDtoList)

        val favoriteProductList = listOf(
            ProductDto(
                category = "Electronics",
                count = 50,
                description = "A high-quality smartphone with a sleek design.",
                id = 1,
                imageOne = "https://example.com/images/product1_img1.jpg",
                imageTwo = "https://example.com/images/product1_img2.jpg",
                imageThree = "https://example.com/images/product1_img3.jpg",
                price = 999.99,
                rate = 4.6,
                salePrice = 899.99,
                saleState = true,
                title = "Smartphone Model X"
            )
        )
        val expectedFavoriteResponse = ProductListDto(favoriteProductList)
        `when`(mockProductRepository.getProducts()).thenReturn(expectedResponse)
        `when`(mockProductRepository.getFavoriteProducts(anyString())).thenReturn(
            expectedFavoriteResponse
        )
        getProductsUseCase("userId").collect { result ->
            assert(result is Resource.Success)
            val data = (result as Resource.Success).data

            assertEquals(1, data.size)
            assertEquals(1, data.first().id)

            val favoriteProduct = data.find { it.id == 1 }
            assertTrue(favoriteProduct?.isFavorite == true)
        }
    }

    @Test
    fun `test getProductsUseCase handles exception`() = runBlocking {
        `when`(mockProductRepository.getProducts()).thenThrow(RuntimeException("API error"))

        getProductsUseCase("userId").collect { result ->
            assert(result is Resource.Error)
            val errorMessage = (result as Resource.Error).message
            assertEquals("API error", errorMessage)
        }
    }
}
