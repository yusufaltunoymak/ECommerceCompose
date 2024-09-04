package com.hoy.ecommercecompose.repositroy

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl
import com.hoy.ecommercecompose.data.source.local.ProductDao
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity
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
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
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
                    imageOne = "",
                    imageTwo = "",
                    imageThree = "",
                    price = 999.99,
                    rate = 4.5,
                    salePrice = 899.99,
                    saleState = true,
                    title = "Smartphone Model X"
                ),
                ProductDto(
                    category = "Home Appliances",
                    count = 20,
                    description = "A powerful and energy-efficient vacuum cleaner.",
                    id = 2,
                    imageOne = "",
                    imageTwo = "",
                    imageThree = "",
                    price = 299.99,
                    rate = 4.7,
                    salePrice = 279.99,
                    saleState = true,
                    title = "Vacuum Cleaner 2000"
                ),
                ProductDto(
                    category = "Books",
                    count = 100,
                    description = "A thrilling mystery novel by a best-selling author.",
                    id = 3,
                    imageOne = "",
                    imageTwo = "",
                    imageThree = "",
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
                    name = "Electronics",
                    image = ""
                ),
                Category(
                    name = "Home Appliances",
                    image = ""
                ),
                Category(name = "Books", image = "")
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
                imageOne = "",
                imageTwo = "",
                imageThree = "",
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
            status = 200,
            message = "Success"
        )
        `when`(
            mockApiService.addToFavorites(
                baseBody = BaseBody(
                    userId = "1",
                    productId = 1
                )
            )
        ).thenReturn(
            expectedBaseResponse
        )
        val result = productRepositoryImpl.addFavoriteProduct(
            baseBody = BaseBody(
                userId = "1",
                productId = 1
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
                    imageOne = "",
                    imageTwo = "",
                    imageThree = "",
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
            status = 200,
            message = "Success"
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

    @Test
    fun `test getCartProductsLocal returns expected data`() = runBlocking<Unit> {
        val userId = "123"
        val expectedCartProducts = listOf(
            ProductEntity(
                productId = 1,
                id = userId,
                category = "Electronics",
                count = 12,
                description = "A high-quality smartphone with a sleek design.",
                imageOne = "",
                imageThree = "",
                imageTwo = "",
                price = 999.99,
                rate = 4.5,
                salePrice = 899.99,
                saleState = true,
                title = "Smartphone Model X",
                isFavorite = false,
                quantity = 1
            )
        )
        `when`(mockProductDao.getCartProducts(userId = userId)).thenReturn(
            flowOf(
                expectedCartProducts
            )
        )
        val resultFlow = productRepositoryImpl.getCartProductsLocal(userId = userId)
        resultFlow.collect { result ->
            assertTrue(result is Resource.Success)
            assertEquals(expectedCartProducts, (result as Resource.Success).data)
        }
        verify(mockProductDao).getCartProducts(userId = userId)
    }

    @Test
    fun `test getCartProductsLocal handles exception`() = runTest {
        val userId = "123"
        val errorMessage = "An error occurred"
        `when`(mockProductDao.getCartProducts(userId)).thenReturn(
            flow {
                throw Exception(errorMessage)
            }
        )
        val resultFlow = productRepositoryImpl.getCartProductsLocal(userId)
        resultFlow.collect { result ->
            assertTrue(result is Resource.Error)
            assertEquals(errorMessage, (result as Resource.Error).message)
        }
        verify(mockProductDao).getCartProducts(userId)
    }

    @Test
    fun `test addToCartProduct`() = runBlocking {
        val product = ProductEntity(
            productId = 1,
            id = "123",
            category = "Electronics",
            count = 15,
            description = "A high-quality smartphone with a sleek design.",
            imageOne = "",
            imageThree = "",
            imageTwo = "",
            price = 999.99,
            rate = 4.5,
            salePrice = 899.99,
            saleState = true,
            title = "Smartphone Model X",
            isFavorite = false,
            quantity = 1
        )
        productRepositoryImpl.addToCartProduct(product)
        verify(mockProductDao).addToCartProduct(product)
    }

    @Test
    fun `test deleteCartProduct`() = runBlocking {
        val productId = 1
        productRepositoryImpl.deleteFromCartProduct(productId)
        verify(mockProductDao).deleteFromCartProduct(productId)
    }

    @Test
    fun `test isProductInCart`() = runBlocking {
        val productId = 1
        `when`(mockProductDao.isProductInCart(productId)).thenReturn(true)
        val result = productRepositoryImpl.isProductInCart(productId)
        assertTrue(result)
    }

    @Test
    fun `test clearCart`() = runBlocking {
        val userId = "123"
        productRepositoryImpl.clearCart(userId)
        verify(mockProductDao).clearCart(userId)
    }

    @Test
    fun `test updateCartProduct`() = runBlocking {
        val product = ProductEntity(
            productId = 1,
            id = "123",
            category = "Electronics",
            count = 36,
            description = "A high-quality smartphone with a sleek design.",
            imageOne = "",
            imageTwo = "",
            imageThree = "",
            price = 999.99,
            rate = 4.5,
            salePrice = 899.99,
            saleState = true,
            title = "Smartphone Model X",
            isFavorite = false,
            quantity = 1
        )
        productRepositoryImpl.updateCartProduct(product)
        verify(mockProductDao).updateCartProduct(product)
    }

    @Test
    fun `test addPaymentDetails`() = runBlocking {
        val payment = PaymentEntity(
            paymentId = 12,
            userId = "123",
            cardNumber = "4111111111111111",
            cardHolderName = "John Doe",
            expirationDate = "12/25",
            city = "Istanbul",
            district = "Kadikoy",
            fullAddress = "123 Example Street",
            productId = 1,
            imageOne = "",
            title = "Smartphone",
            quantity = 2,
            price = 999.99
        )
        productRepositoryImpl.addPaymentDetails(payment)
        verify(mockProductDao).addPaymentDetails(payment)
    }

    @Test
    fun `test getUserOrders`() = runBlocking {
        val expectedOrder = listOf(
            PaymentEntity(
                paymentId = 12,
                userId = "123",
                cardNumber = "4111111111111111",
                cardHolderName = "John Doe",
                expirationDate = "12/25",
                city = "Istanbul",
                district = "Kadikoy",
                fullAddress = "123 Example Street",
                productId = 1,
                imageOne = "",
                title = "Smartphone",
                quantity = 2,
                price = 999.99
            )
        )
        `when`(mockProductDao.getUserOrders("123")).thenReturn(flowOf(expectedOrder))
        val resultFlow = productRepositoryImpl.getUserOrders("123")
        resultFlow.collect { result ->
            assertEquals(expectedOrder, result)
        }
    }

    @Test
    fun `test processOrder`() = runBlocking {
        val userId = "123"
        val expectedPayment = PaymentEntity(
            paymentId = 12,
            userId = "123",
            cardNumber = "4111111111111111",
            cardHolderName = "John Doe",
            expirationDate = "12/25",
            city = "Istanbul",
            district = "Kadikoy",
            fullAddress = "123 Example Street",
            productId = 1,
            imageOne = "",
            title = "Smartphone",
            quantity = 2,
            price = 999.99
        )
        productRepositoryImpl.processOrder(userId, expectedPayment)
        verify(mockProductDao).processOrder(userId, expectedPayment)
    }
}
