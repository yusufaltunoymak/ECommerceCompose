package com.hoy.ecommercecompose.ui.favorite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.ECEmptyScreen
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.displayFontFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    uiState: FavoriteContract.UiState,
    onAction: (FavoriteContract.UiAction) -> Unit,
    uiEffect: Flow<FavoriteContract.UiEffect>,
    onNavigateToDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is FavoriteContract.UiEffect.FavoriteProductDetailClick -> onNavigateToDetail(
                        effect.productId
                    )

                    is FavoriteContract.UiEffect.ShowError -> TODO()
                    is FavoriteContract.UiEffect.BackScreen -> onBackClick()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.fav_product)) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = LocalColors.current.red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.favoriteProducts.isEmpty() -> {
                    ECEmptyScreen(
                        title = R.string.empty_fav_title,
                        description = R.string.empty_fav_desc,
                        icon = R.drawable.ic_heart
                    )
                }

                else -> {
                    LazyColumn {
                        items(uiState.favoriteProducts) { product ->
                            FavoriteProductCard(
                                product = product,
                                onFavoriteClick = {
                                    onAction(FavoriteContract.UiAction.DeleteFromFavorites(product.id))
                                },
                                onNavigateToDetail = {
                                    onNavigateToDetail(product.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteProductCard(
    product: ProductUi,
    onFavoriteClick: (ProductUi) -> Unit,
    onNavigateToDetail: (Int) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetail(product.id) }
            .padding(LocalDimensions.current.eight)
            .clip(RoundedCornerShape(LocalDimensions.current.twelve))
            .shadow(
                LocalDimensions.current.four,
                RoundedCornerShape(LocalDimensions.current.twelve)
            ),
        border = BorderStroke(LocalDimensions.current.one, LocalColors.current.gray),
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.white
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
                contentDescription = stringResource(id = R.string.desc_product),
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
                    color = LocalColors.current.darkGray,
                    fontFamily = displayFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(LocalDimensions.current.eight))

                Row {
                    Text(
                        text = "$${product.price}",
                        fontFamily = displayFontFamily,
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                        color = LocalColors.current.gray
                    )
                    Spacer(modifier = Modifier.width(LocalDimensions.current.eight))
                    Text(
                        text = "$${product.salePrice}",
                        fontFamily = displayFontFamily,
                        style = MaterialTheme.typography.bodyLarge,
                        color = LocalColors.current.red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(LocalDimensions.current.eight))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(id = R.string.rating),
                        tint = LocalColors.current.primary,
                        modifier = Modifier.size(LocalDimensions.current.sixteen)
                    )
                    Spacer(modifier = Modifier.width(LocalDimensions.current.four))
                    Text(
                        text = product.rate.toString(),
                        fontFamily = displayFontFamily,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalColors.current.gray
                    )
                }
            }

            IconButton(
                onClick = { onFavoriteClick(product) },
                modifier = Modifier
                    .background(color = LocalColors.current.white, shape = CircleShape)
                    .padding(LocalDimensions.current.four)
                    .size(LocalDimensions.current.thirtySix)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = LocalColors.current.gray,
                    modifier = Modifier.size(LocalDimensions.current.twenty)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteProductCardPreview() {
    FavoriteScreen(
        uiState = FavoriteContract.UiState(),
        onAction = {},
        uiEffect = flowOf(),
        onNavigateToDetail = {},
        onBackClick = {}
    )
}

