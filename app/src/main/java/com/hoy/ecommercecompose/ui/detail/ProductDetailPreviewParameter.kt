package com.hoy.ecommercecompose.ui.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hoy.ecommercecompose.data.source.remote.model.ProductDetail

class ProductDetailPreviewParameter : PreviewParameterProvider<ProductDetailContract.UiState> {
    override val values: Sequence<ProductDetailContract.UiState>
        get() = sequenceOf(
            ProductDetailContract.UiState(
                isLoading = false,
                productDetail = ProductDetail(
                    description = "Description 1",
                    id = 1,
                    imageOne = "https://via.placeholder.com/150",
                    imageThree = "https://via.placeholder.com/150",
                    imageTwo = "https://via.placeholder.com/150",
                    price = 100.0,
                    title = "Product 1",
                    isFavorite = false,
                    rate = 4.5,
                    salePrice = 90.0,
                    saleState = true
                ),
                addToCart = null,
                errorMessage = null,
            ),
            ProductDetailContract.UiState(
                isLoading = true,
                productDetail = null,
                addToCart = null,
                errorMessage = null,
            ),
            ProductDetailContract.UiState(
                isLoading = false,
                productDetail = null,
                addToCart = null,
                errorMessage = "Error message",
            ),
        )
}