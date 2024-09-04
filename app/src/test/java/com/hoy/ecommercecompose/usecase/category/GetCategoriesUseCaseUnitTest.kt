package com.hoy.ecommercecompose.usecase.category

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.category.GetCategoriesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetCategoriesUseCaseUnitTest {
    @Mock
    lateinit var mockProductRepository: ProductRepository

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getCategoriesUseCase = GetCategoriesUseCase(mockProductRepository)
    }

    @Test
    fun `test getCategoriesUseCase returns distinct categories`() = runBlocking {
        val categoryList = listOf(
            Category(
                name = "Electronics",
                image = "",
            ),
            Category(
                name = "Electronics",
                image = "",
            ),
            Category(
                name = "Fruits",
                image = "",
            )
        )
        val expectedResponse = GetCategoriesResponse(categories = categoryList)
        `when`(mockProductRepository.getCategories()).thenReturn(expectedResponse)

        getCategoriesUseCase().collect { result ->
            assert(result is Resource.Success)
            val data = (result as Resource.Success).data
            assertEquals(2, data.size)
            assertTrue(data.any { it.name == "Electronics" })
            assertTrue(data.any { it.name == "Fruits" })
            assertTrue(data.all { it.name.isNotEmpty() })
        }
    }

    @Test
    fun `test getCategoriesUseCase handles exception`() = runBlocking {
        `when`(mockProductRepository.getCategories()).thenThrow(RuntimeException("API error"))
        getCategoriesUseCase().collect { result ->
            assert(result is Resource.Error)
            val errorMessage = (result as Resource.Error).message
            assertEquals("API error", errorMessage)
        }
    }
}
