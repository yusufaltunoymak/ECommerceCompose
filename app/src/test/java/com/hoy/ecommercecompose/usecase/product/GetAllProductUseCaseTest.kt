package com.hoy.ecommercecompose.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.product.GetAllProductUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetAllProductUseCaseTest {
    @Mock
    lateinit var mockProductRepository: ProductRepository

    private lateinit var getAllProductUseCase: GetAllProductUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getAllProductUseCase = GetAllProductUseCase(mockProductRepository)
    }

    @Test
    fun `test getAllProductUseCase return filtered products`() = runBlocking {
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
                rate = 4.5,
                salePrice = 899.99,
                saleState = true,
                title = "Smartphone Model X"
            ), ProductDto(
                category = "Home Appliances",
                count = 20,
                description = "A powerful and energy-efficient vacuum cleaner.",
                id = 2,
                imageOne = "https://example.com/images/product2_img1.jpg",
                imageTwo = "https://example.com/images/product2_img2.jpg",
                imageThree = "https://example.com/images/product2_img3.jpg",
                price = 299.99,
                rate = 4.7,
                salePrice = 279.99,
                saleState = true,
                title = "Banana"
            ), ProductDto(
                category = "Books",
                count = 100,
                description = "A thrilling mystery novel by a best-selling author.",
                id = 3,
                imageOne = "https://example.com/images/product3_img1.jpg",
                imageTwo = "https://example.com/images/product3_img2.jpg",
                imageThree = "https://example.com/images/product3_img3.jpg",
                price = 19.99,
                rate = 4.9,
                salePrice = null,
                saleState = false,
                title = "The Mysterious Affair at Styles"
            )
        )
        val expectedResponse = ProductListDto(productDtoList)
        `when`(mockProductRepository.getProducts()).thenReturn(expectedResponse)
        getAllProductUseCase("banana").collect { result ->
            println("Result: $result")
            assert(result is Resource.Success)
            val data = (result as Resource.Success).data
            assertEquals(1, data.size)
            assertEquals("Banana", data.first().title)
        }
    }

    @Test
    fun `test getAllProductUseCase handles exception`() = runBlocking {
        `when`(mockProductRepository.getProducts()).thenThrow(RuntimeException("API error"))
        getAllProductUseCase("Banana").collect { result ->
            assert(result is Resource.Error)
            val errorMessage = (result as Resource.Error).message
            assertEquals("API error", errorMessage)
        }
    }
}