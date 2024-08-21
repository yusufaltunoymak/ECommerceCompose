package com.hoy.ecommercecompose.ui.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit,
    uiState: CategoryContract.UiState,
    onAction: (CategoryContract.UiAction) -> Unit,
    uiEffect: Flow<CategoryContract.UiEffect>,
    onBackClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.searchQuery) {
        if (uiState.searchQuery.isEmpty()) {
            val category = viewModel.getCategory()
            viewModel.getProductsByCategory(category)
        } else {
            viewModel.searchProducts(uiState.searchQuery)
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(LocalDimensions.current.sixteen)
        ) {
            Row {
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier.size(LocalDimensions.current.fortyEight)
                ) {
                    Icon(
                        modifier = Modifier.size(LocalDimensions.current.thirtyEight),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
                CustomSearchView(
                    text = uiState.searchQuery,
                    onTextChange = { query ->
                        uiState.searchQuery = query
                        viewModel.searchProducts(query)
                    },
                    placeHolder = stringResource(id = R.string.search_for_products),
                    onCloseClicked = {
                        viewModel.changeQuery("")
                    },
                    onSearchClick = {
                        // Handle search button click if needed
                    },
                    onSortClick = { expanded = !expanded }
                )
            }

            Spacer(modifier = Modifier.height(LocalDimensions.current.sixteen))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    uiState.errorMessage != null -> {
                        Text(
                            text = uiState.errorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    uiState.categoryList.isEmpty() -> {
                        Text(
                            text = stringResource(id = R.string.no_products_found),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        ProductCategoryList(
                            uiState = uiState,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.price_low_to_high), style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        viewModel.sortProducts(SortOption.PRICE_LOW_TO_HIGH)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.price_high_to_low), style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        viewModel.sortProducts(SortOption.PRICE_HIGH_TO_LOW)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.rating), style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        viewModel.sortProducts(SortOption.RATING)
                        expanded = false
                    }
                )
            }
        }
    }
}

enum class SortOption {
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    RATING
}

@Composable
fun ProductCategoryCard(
    product: ProductUi,
    onProductDetailClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onProductDetailClick(product.id) }
            .fillMaxWidth()
            .padding(LocalDimensions.current.eight)
            .clip(RoundedCornerShape(LocalDimensions.current.twelve))
            .shadow(
                LocalDimensions.current.four,
                RoundedCornerShape(LocalDimensions.current.twelve)
            ),
        border = BorderStroke(LocalDimensions.current.one, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(LocalDimensions.current.twelve)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(product.imageOne)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(LocalDimensions.current.oneHundred)
                    .clip(RoundedCornerShape(LocalDimensions.current.eight))
            )

            Spacer(modifier = Modifier.width(LocalDimensions.current.twelve))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = LocalDimensions.current.eight)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(LocalDimensions.current.six))

                Row {
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(LocalDimensions.current.six))
                    Text(
                        text = "$${product.salePrice}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(LocalDimensions.current.six))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = LocalColors.current.primary,
                        modifier = Modifier.size(LocalDimensions.current.sixteen)
                    )
                    Spacer(modifier = Modifier.width(LocalDimensions.current.four))
                    Text(
                        text = product.rate.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCategoryList(
    uiState: CategoryContract.UiState,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(uiState.categoryList) { product ->
            ProductCategoryCard(product = product, onProductDetailClick = onNavigateToDetail)
        }
    }
}
