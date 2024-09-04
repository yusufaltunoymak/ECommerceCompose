package com.hoy.ecommercecompose.usecase.product

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.CheckFavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import com.hoy.ecommercecompose.domain.usecase.product.GetProductDetailUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetProductDetailUseCaseTest {

    @Mock
    lateinit var mockProductRepository: ProductRepository

    private lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getProductDetailUseCase = GetProductDetailUseCase(mockProductRepository)
    }

    @Test
    fun `test getProductDetailUseCase returns product detail successfully`() = runBlocking {
        val productDetailDto = ProductDto(
            id = 1,
            title = "Smartphone Model X",
            category = "Electronics",
            count = 50,
            description = "A high-quality smartphone with a sleek design.",
            imageOne = "https://example.com/images/product1_img1.jpg",
            imageTwo = "https://example.com/images/product1_img2.jpg",
            imageThree = "https://example.com/images/product1_img3.jpg",
            price = 999.99,
            rate = 4.5,
            salePrice = 899.99,
            saleState = true
        )
        val expectedProductDetailResponse = GetProductDetailResponse(productDto = productDetailDto)
        val expectedFavoriteResponse = CheckFavoriteResponse(isFavorite = true)

        `when`(mockProductRepository.getProductDetail(1)).thenReturn(expectedProductDetailResponse)
        `when`(mockProductRepository.checkProductIsFavorite("userId", 1)).thenReturn(
            expectedFavoriteResponse
        )

        val result = getProductDetailUseCase("userId", 1)
        assert(result is Resource.Success)
        val productDetail = (result as Resource.Success).data
        assertEquals(1, productDetail.id)
        assertEquals("Smartphone Model X", productDetail.title)
        assertTrue(productDetail.isFavorite == true)
    }

    @Test
    fun `test getProductDetailUseCase returns error when product not found`() = runBlocking {
        val expectedProductDetailResponse = GetProductDetailResponse(productDto = null)
        `when`(mockProductRepository.getProductDetail(1)).thenReturn(expectedProductDetailResponse)
        val result = getProductDetailUseCase("userId", 1)
        assert(result is Resource.Error)
        val errorMessage = (result as Resource.Error).message
        assertEquals("Product not found", errorMessage)
    }
}