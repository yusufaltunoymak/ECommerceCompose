package com.hoy.ecommercecompose.usecase.category

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.category.GetProductsByCategoryUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetProductsByCategoryUseCaseUnitTest {
    @Mock
    lateinit var mockProductRepository: ProductRepository

    private lateinit var getProductsByCategoryUseCase: GetProductsByCategoryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getProductsByCategoryUseCase = GetProductsByCategoryUseCase(mockProductRepository)
    }

    @Test
    fun `test getProductsByCategoryUseCase returns products by category`() = runBlocking {
        val productDtoList = listOf(
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
                category = "Electronics",
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
        val expectedResponse = ProductListDto(productDtoList)
        `when`(mockProductRepository.getByCategory("Electronics")).thenReturn(expectedResponse)
        getProductsByCategoryUseCase("Electronics").collect { result ->
            assert(result is Resource.Success)
            val data = (result as Resource.Success).data
            assertEquals(2, data.size)
            assertEquals("Product 1", data[0].title)
            assertEquals("Product 2", data[1].title)
        }
    }

    @Test
    fun `test getProductsByCategoryUseCase handles exception`() = runBlocking {
        `when`(mockProductRepository.getByCategory("Electronics")).thenThrow(RuntimeException("API error"))
        getProductsByCategoryUseCase("Electronics").collect { result ->
            assert(result is Resource.Error)
            val errorMessage = (result as Resource.Error).message
            assertEquals("API error", errorMessage)
        }
    }
}
