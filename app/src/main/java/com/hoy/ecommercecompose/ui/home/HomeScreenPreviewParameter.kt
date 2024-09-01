package com.hoy.ecommercecompose.ui.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.domain.model.ProductUi

class HomeScreenPreviewParameter : PreviewParameterProvider<HomeContract.UiState> {
    override val values: Sequence<HomeContract.UiState>
        get() = sequenceOf(
            HomeContract.UiState(
                isLoading = false,
                productList = emptyList(),
                categoryList = emptyList(),
                specialProductList = emptyList(),
            ),
            HomeContract.UiState(
                isLoading = true,
                productList = emptyList(),
                categoryList = emptyList(),
                specialProductList = emptyList(),
            ),
            HomeContract.UiState(
                isLoading = false,
                categoryList = listOf(
                    Category(
                        name = "Category 1",
                        image = "https://via.placeholder.com/150",
                    ),
                    Category(
                        name = "Category 2",
                        image = "https://via.placeholder.com/150",
                    ),
                    Category(
                        name = "Category 3",
                        image = "https://via.placeholder.com/150",
                    ),
                ),
                productList = listOf(
                    ProductUi(
                        id = 1,
                        title = "Product 1",
                        price = 100.0,
                        imageOne = "https://via.placeholder.com/150",
                        category = "Category 1",
                        count = 1,
                        description = "Description 1",
                        imageTwo = "https://via.placeholder.com/150",
                        imageThree = "https://via.placeholder.com/150",
                        isFavorite = false,
                        rate = 4.5,
                        salePrice = 90.0,
                        saleState = true,
                    ),
                    ProductUi(
                        id = 2,
                        title = "Product 2",
                        price = 200.0,
                        imageOne = "https://via.placeholder.com/150",
                        category = "Category 2",
                        count = 2,
                        description = "Description 2",
                        imageTwo = "https://via.placeholder.com/150",
                        imageThree = "https://via.placeholder.com/150",
                        isFavorite = true,
                        rate = 4.0,
                        salePrice = 180.0,
                        saleState = false,
                    ),
                    ProductUi(
                        id = 3,
                        title = "Product 3",
                        price = 300.0,
                        imageOne = "https://via.placeholder.com/150",
                        category = "Category 3",
                        count = 3,
                        description = "Description 3",
                        imageTwo = "https://via.placeholder.com/150",
                        imageThree = "https://via.placeholder.com/150",
                        isFavorite = false,
                        rate = 3.5,
                        salePrice = 270.0,
                        saleState = true,
                    ),
                ),
                specialProductList = listOf(
                    ProductUi(
                        id = 4,
                        title = "Special Product 1",
                        price = 400.0,
                        imageOne = "https://via.placeholder.com/150",
                        category = "Category 1",
                        count = 4,
                        description = "Special Description 1",
                        imageTwo = "https://via.placeholder.com/150",
                        imageThree = "https://via.placeholder.com/150",
                        isFavorite = true,
                        rate = 4.5,
                        salePrice = 360.0,
                        saleState = false,
                    ),
                    ProductUi(
                        id = 5,
                        title = "Special Product 2",
                        price = 500.0,
                        imageOne = "https://via.placeholder.com/150",
                        category = "Category 2",
                        count = 5,
                        description = "Special Description 2",
                        imageTwo = "https://via.placeholder.com/150",
                        imageThree = "https://via.placeholder.com/150",
                        isFavorite = false,
                        rate = 4.0,
                        salePrice = 450.0,
                        saleState = true,
                    ),
                    ProductUi(
                        id = 6,
                        title = "Special Product 3",
                        price = 600.0,
                        imageOne = "https://via.placeholder.com/150",
                        category = "Category 3",
                        count = 6,
                        description = "Special Description 3",
                        imageTwo = "https://via.placeholder.com/150",
                        imageThree = "https://via.placeholder.com/150",
                        isFavorite = true,
                        rate = 3.5,
                        salePrice = 540.0,
                        saleState = false,
                    ),
                )
            ),
        )
}
