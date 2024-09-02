package com.hoy.ecommercecompose.ui.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CustomAlertDialog
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryScreen(
    uiState: CategoryContract.UiState,
    onAction: (CategoryContract.UiAction) -> Unit,
    uiEffect: Flow<CategoryContract.UiEffect>,
    onNavigateToDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    var alertDialogState by remember { mutableStateOf(false) }

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is CategoryContract.UiEffect.ShowError -> alertDialogState = true
            is CategoryContract.UiEffect.NavigateBack -> onBackClick()
            is CategoryContract.UiEffect.DetailScreen -> onNavigateToDetail(effect.id)
        }
    }

    if (alertDialogState) {
        uiState.errorMessage?.let {
            CustomAlertDialog(
                errorMessage = it,
                onDismiss = {
                    onAction(CategoryContract.UiAction.ClearError)
                    alertDialogState = false
                }
            )
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(ECTheme.dimensions.sixteen)
        ) {
            Row {
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .size(ECTheme.dimensions.fiftySix)
                        .border(
                            BorderStroke(
                                ECTheme.dimensions.one,
                                ECTheme.colors.primary.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(ECTheme.dimensions.twelve)
                        )
                ) {
                    Icon(
                        modifier = Modifier.size(ECTheme.dimensions.thirtyEight),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(ECTheme.dimensions.eight))

                CustomSearchView(
                    text = uiState.searchQuery,
                    onTextChange = { query ->
                        uiState.searchQuery = query
                        onAction(CategoryContract.UiAction.SearchProducts(query))
                    },
                    placeHolder = stringResource(id = R.string.search_for_products),
                    onCloseClicked = {
                        onAction(CategoryContract.UiAction.ChangeQuery(uiState.searchQuery))
                    },
                    onSearchClick = {
                        // Handle search button click if needed
                    },
                )
            }

            Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    uiState.errorMessage != null -> {
                        Text(
                            text = uiState.errorMessage,
                            color = ECTheme.colors.red,
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
        }
    }
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
            .padding(ECTheme.dimensions.eight)
            .clip(RoundedCornerShape(ECTheme.dimensions.twelve))
            .shadow(
                ECTheme.dimensions.four,
                RoundedCornerShape(ECTheme.dimensions.twelve)
            ),
        border = BorderStroke(ECTheme.dimensions.one, ECTheme.colors.gray),
        colors = CardDefaults.cardColors(
            containerColor = ECTheme.colors.white
        )
    ) {
        Row(
            modifier = Modifier
                .padding(ECTheme.dimensions.twelve)
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
                    .size(ECTheme.dimensions.oneHundred)
                    .clip(RoundedCornerShape(ECTheme.dimensions.eight))
            )

            Spacer(modifier = Modifier.width(ECTheme.dimensions.twelve))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = ECTheme.dimensions.eight)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ECTheme.colors.darkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(ECTheme.dimensions.six))

                Row {
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                        color = ECTheme.colors.gray
                    )
                    Spacer(modifier = Modifier.width(ECTheme.dimensions.six))
                    Text(
                        text = "$${product.salePrice}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = ECTheme.colors.red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(ECTheme.dimensions.six))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = ECTheme.colors.primary,
                        modifier = Modifier.size(ECTheme.dimensions.sixteen)
                    )
                    Spacer(modifier = Modifier.width(ECTheme.dimensions.four))
                    Text(
                        text = product.rate.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = ECTheme.colors.gray
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
