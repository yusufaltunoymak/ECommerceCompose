package com.hoy.ecommercecompose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CategoryList
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.components.ProductList
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToSearch: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

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

        SearchNavigationView(
            onNavigateToSearch = onNavigateToSearch,
            modifier = Modifier.fillMaxWidth()
        )
        CustomHorizontalPager(imageUrls = imageUrls)

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
        ProductList(
            uiState = uiState,
            onFavoriteClick = { product ->
                viewModel.toggleFavorite(userId, product)
            },
            onNavigateToDetail = onNavigateToDetail
        )

    }
}

@Composable
fun SearchNavigationView(
    modifier: Modifier = Modifier,
    onNavigateToSearch: () -> Unit
) {
    val containerColor = Color.White
    val indicatorColor = LocalColors.current.primary.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onNavigateToSearch() // Navigate to SearchScreen
            }
            .background(containerColor) // Set background color
            .border(1.dp, indicatorColor, RoundedCornerShape(8.dp)) // Add border
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(start = 4.dp, end = 8.dp)
            )
            Text(
                text = "Search for products",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "List Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
    }
}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview() {
//    HomeScreen(
//        uiState = HomeUiState(
//            currentUser = User("John Doe"),
//            productList = listOf(
//                ProductUi(
//                    category = "Electronics",
//                    count = 10,
//                    description = "Description",
//                    id = 1,
//                    imageOne = "https://via.placeholder.com/150",
//                    price = 100.0,
//                    title = "Product1",
//                    isFavorite = false,
//                    rate = 4.5
//                )
//                )
//        ),
//        navController = rememberNavController()
//    )
//}