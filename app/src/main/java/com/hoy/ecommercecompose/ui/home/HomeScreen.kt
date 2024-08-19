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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CategoryList
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.components.ProductList
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.displayFontFamily
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    uiEffect: Flow<HomeContract.UiEffect>,
    uiState: HomeContract.UiState,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToSearch: () -> Unit,
    onCategoryListClick: (String) -> Unit,
    onAction: (HomeContract.UiAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    val imageUrls = listOf(
        R.drawable.sale,
        R.drawable.sale2,
        R.drawable.sale
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is HomeContract.UiEffect.ProductCartClick -> onNavigateToDetail(effect.id)
                    is HomeContract.UiEffect.SearchClick -> onNavigateToSearch()
                    is HomeContract.UiEffect.CategoryClick -> onCategoryListClick(effect.category)
                    is HomeContract.UiEffect.ShowError -> TODO()
                }
            }
        }
    }

    Column(
        modifier = Modifier
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
        CategoryList(
            uiState = uiState,
            onCategoryListClick = onCategoryListClick
        )

        Text(
            text = "Top Rated Products",
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray,
            fontFamily = displayFontFamily
        )
        ProductList(
            uiState = uiState,
            onFavoriteClick = { product ->
                onAction(HomeContract.UiAction.ToggleFavoriteClick(product))
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
                onNavigateToSearch()
            }
            .background(containerColor)
            .border(1.dp, indicatorColor, RoundedCornerShape(8.dp))
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

// @Preview(showBackground = true, showSystemUi = true)
// @Composable
// fun Preview() {
//    HomeScreen(
//        onNavigateToDetail = {},
//        onNavigateToSearch = {},
//        onCategoryListClick = {},
//        onAction = {},
//        uiEffect = Flow< HomeContract.UiEffect>,
//        uiState = HomeContract.UiState(),
//    )
// }
