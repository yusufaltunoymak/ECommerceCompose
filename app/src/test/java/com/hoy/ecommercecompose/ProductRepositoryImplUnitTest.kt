package com.hoy.ecommercecompose

import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl
import com.hoy.ecommercecompose.data.source.local.ProductDao
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.data.source.remote.model.CheckFavoriteResponse
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetCategoriesResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.GetProductDetailResponse
import com.hoy.ecommercecompose.data.source.remote.model.response.ProductListDto
import com.hoy.ecommercecompose.domain.model.BaseBody
import com.hoy.ecommercecompose.domain.model.DeleteFromFavoriteBody
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ProductRepositoryImplUnitTest {

    @Mock
    lateinit var mockApiService: ApiService

    @Mock
    lateinit var mockProductDao: ProductDao

    private lateinit var productRepositoryImpl: ProductRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productRepositoryImpl = ProductRepositoryImpl(mockApiService, mockProductDao)
    }

    @Test
    fun `test getProducts returns expected data`() = runBlocking<Unit> {
        val expectedProducts = ProductListDto(
            productDto = listOf(
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
                    title = "Vacuum Cleaner 2000"
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
        )

        `when`(mockApiService.getProducts()).thenReturn(expectedProducts)

        val result = productRepositoryImpl.getProducts()

        assertEquals(expectedProducts, result)
        verify(mockApiService).getProducts()
    }

    @Test
    fun `test getCategories returns expected data`() = runBlocking<Unit> {
        val expectedCategories = GetCategoriesResponse(
            categories = listOf(
                Category(
                    name = "Electronics", image = "https://example.com/images/product1_img1.jpg"
                ), Category(
                    name = "Home Appliances", image = "https://example.com/images/product2_img1.jpg"
                ), Category(name = "Books", image = "https://example.com/images/product3_img1.jpg")
            )
        )
        `when`(mockApiService.getCategories()).thenReturn(expectedCategories)
        val result = productRepositoryImpl.getCategories()
        assertEquals(expectedCategories, result)
        verify(mockApiService).getCategories()
    }

    @Test
    fun `test getProductDetail returns expected data`() = runBlocking {
        val expectedProductDetail = GetProductDetailResponse(
            productDto = ProductDto(
                category = "Electronics",
                count = 12,
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
            )
        )
        `when`(mockApiService.getProductDetail(id = 1)).thenReturn(expectedProductDetail)
        val result = productRepositoryImpl.getProductDetail(id = 1)
        assertEquals(expectedProductDetail, result)
    }

    @Test
    fun `test checkProductIsFavorite returns expected data`() = runBlocking<Unit> {
        val expectedCheckFavoriteResponse = CheckFavoriteResponse(
            isFavorite = true
        )
        `when`(mockApiService.checkIsFavorite(userId = "1", productId = 1)).thenReturn(
            expectedCheckFavoriteResponse
        )
        val result = productRepositoryImpl.checkProductIsFavorite(userId = "1", productId = 1)
        assertEquals(expectedCheckFavoriteResponse, result)
        verify(mockApiService).checkIsFavorite(userId = "1", productId = 1)
    }

    @Test
    fun `test addFavoriteProduct returns expected data`() = runBlocking<Unit> {
        val expectedBaseResponse = BaseResponse(
            status = 200, message = "Success"
        )
        `when`(
            mockApiService.addToFavorites(
                baseBody = BaseBody(
                    userId = "1", productId = 1
                )
            )
        ).thenReturn(
            expectedBaseResponse
        )
        val result = productRepositoryImpl.addFavoriteProduct(
            baseBody = BaseBody(
                userId = "1", productId = 1
            )
        )
        assertEquals(expectedBaseResponse, result)
        verify(mockApiService).addToFavorites(baseBody = BaseBody(userId = "1", productId = 1))
    }

    @Test
    fun `test getFavoriteProducts returns expected data`() = runBlocking<Unit> {
        val expectedProductListDto = ProductListDto(
            productDto = listOf(
                ProductDto(
                    category = "Electronics",
                    count = 40,
                    description = "A high-quality smartphone with a sleek design.",
                    id = 1,
                    imageOne = "https://example.com/images/product1_img1.jpg",
                    imageTwo = "https://example.com/images/product",
                    imageThree = "https://example.com/images/product",
                    price = 999.99,
                    rate = 4.5,
                    salePrice = 899.99,
                    saleState = true,
                    title = "Smartphone Model X",
                )
            )
        )
        `when`(mockApiService.getFavorites(userId = "1")).thenReturn(expectedProductListDto)
        val result = productRepositoryImpl.getFavoriteProducts(userId = "1")
        assertEquals(expectedProductListDto, result)
        verify(mockApiService).getFavorites(userId = "1")
    }

    @Test
    fun `test deleteFavoriteProduct returns expected data`() = runBlocking<Unit> {
        val expectedBaseResponse = BaseResponse(
            status = 200, message = "Success"
        )
        `when`(
            mockApiService.deleteFromFavorites(
                deleteFromFavoriteBody = DeleteFromFavoriteBody(
                    userId = "1",
                    id = 1
                )
            )
        )
            .thenReturn(expectedBaseResponse)
        val result = productRepositoryImpl.deleteFavoriteProduct(
            deleteFromFavoriteBody = DeleteFromFavoriteBody(
                userId = "1",
                id = 1
            )
        )
        assertEquals(expectedBaseResponse, result)
        verify(mockApiService).deleteFromFavorites(
            deleteFromFavoriteBody = DeleteFromFavoriteBody(
                userId = "1",
                id = 1
            )
        )
    }

    @Test
    fun `test getByCategory returns expected data`() = runBlocking<Unit> {
        val expectedByCategory = ProductListDto(
            productDto = listOf(
                ProductDto(
                    category = "Electronics",
                    count = 12,
                    description = "A high-quality smartphone with a sleek design.",
                    id = 1,
                    imageOne = "https://example.com/images/product1_img1.jpg",
                    imageTwo = "https://example.com/images/product",
                    imageThree = "https://example.com/images/product",
                    price = 999.99,
                    rate = 4.5,
                    salePrice = 899.99,
                    saleState = true,
                    title = "Smartphone Model X",
                ),
                ProductDto(
                    category = "Electronics",
                    count = 16,
                    description = "A high-quality smartphone with a sleek design.",
                    id = 2,
                    imageOne = "https://example.com/images/product1_img1.jpg",
                    imageTwo = "https://example.com/images/product",
                    imageThree = "https://example.com/images/product",
                    price = 999.99,
                    rate = 4.5,
                    salePrice = 899.99,
                    saleState = true,
                    title = "Smartphone Model X",
                )
            )
        )
        `when`(mockApiService.getProductsByCategory(category = "Electronics")).thenReturn(
            expectedByCategory
        )
        val result = productRepositoryImpl.getByCategory(category = "Electronics")
        assertEquals(expectedByCategory, result)
        verify(mockApiService).getProductsByCategory(category = "Electronics")
    }
}