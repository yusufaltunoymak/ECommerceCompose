package com.hoy.ecommercecompose.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.source.remote.model.User
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CategoryList
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import com.hoy.ecommercecompose.ui.components.ProductGrid
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    val imageUrls = listOf(
        R.drawable.sale,
        R.drawable.sale2,
        R.drawable.sale
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        with(uiState) {
            if (currentUser != null) {
                Text(
                    text = "Welcome ${currentUser.name}!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    fontFamily = displayFontFamily
                )
            }
        }

        CustomSearchView(
            text = "",
            onTextChange = { /*TODO*/ },
            placeHolder = "Search for products",
            onCloseClicked = { /*TODO*/ }) {
        }

        CustomHorizontalPager(
            imageUrls = imageUrls,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray,
            fontFamily = displayFontFamily
        )
            CategoryList(uiState)

        Text(
            text = "Top Rated Products",
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray,
            fontFamily = displayFontFamily
        )
        ProductGrid(uiState = uiState, navController = navController)

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    HomeScreen(
        uiState = HomeUiState(
            currentUser = User("John Doe"),
            productList = listOf(
                ProductUi(
                    category = "Electronics",
                    count = 10,
                    description = "Description",
                    id = 1,
                    imageOne = "https://via.placeholder.com/150",
                    price = 100.0,
                    title = "Product1",
                    isFavorite = false,
                    rate = 4.5
                )
                )
        ),
        navController = rememberNavController()
    )
}
