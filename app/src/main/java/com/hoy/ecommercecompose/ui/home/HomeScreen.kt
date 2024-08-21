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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CategoryList
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.components.ProductList
import com.hoy.ecommercecompose.ui.theme.ECTheme
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
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
            .padding(ECTheme.dimensions.sixteen)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        with(uiState) {
            if (currentUser != null) {
                Text(
                    text = stringResource(id = R.string.welcome_user, currentUser.name!!),
                    style = MaterialTheme.typography.bodyLarge,
                    color = ECTheme.colors.darkGray,
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
            text = stringResource(id = R.string.categories),
            style = MaterialTheme.typography.titleLarge,
            color = ECTheme.colors.darkGray,
            fontFamily = displayFontFamily
        )
        CategoryList(
            uiState = uiState,
            onCategoryListClick = onCategoryListClick
        )

        Text(
            text = stringResource(id = R.string.top_rated_products),
            style = MaterialTheme.typography.titleLarge,
            color = ECTheme.colors.darkGray,
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
    val containerColor = ECTheme.colors.white
    val indicatorColor = ECTheme.colors.primary.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ECTheme.dimensions.eight))
            .clickable {
                onNavigateToSearch()
            }
            .background(containerColor)
            .border(
                ECTheme.dimensions.one,
                indicatorColor,
                RoundedCornerShape(ECTheme.dimensions.eight)
            )
            .padding(ECTheme.dimensions.sixteen)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_icon),
                modifier = Modifier
                    .padding(
                        start = ECTheme.dimensions.four,
                        end = ECTheme.dimensions.eight
                    )
            )
            Text(
                text = stringResource(id = R.string.search_for_products),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = stringResource(id = R.string.list_icon),
                modifier = Modifier
                    .padding(end = ECTheme.dimensions.eight)
            )
        }
    }
}
