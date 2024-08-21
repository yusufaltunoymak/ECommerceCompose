package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.home.HomeContract
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun ProductCard(
    product: ProductUi,
    modifier: Modifier = Modifier,
    onFavoriteClick: (ProductUi) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val iconColor =
        if (product.isFavorite) LocalColors.current.primary else LocalColors.current.gray
    Card(
        modifier = modifier
            .size(
                LocalDimensions.current.oneHundredSeventy,
                LocalDimensions.current.twoHundredSixty
            )
            .clip(RoundedCornerShape(LocalDimensions.current.twelve))
            .clickable {
                onNavigateToDetail(product.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.white
        ),
        elevation = CardDefaults.cardElevation(LocalDimensions.current.eight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalDimensions.current.oneHundredFifty)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(product.imageOne)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.product_image),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(LocalDimensions.current.twelve))
                )

                IconButton(
                    onClick = { onFavoriteClick(product) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            top = LocalDimensions.current.eight,
                            end = LocalDimensions.current.eight
                        )
                        .background(color = LocalColors.current.lightGray, shape = CircleShape)
                        .size(LocalDimensions.current.thirtySix)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = iconColor,
                    )
                }
            }

            Spacer(modifier = Modifier.height(LocalDimensions.current.eight))

            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = displayFontFamily,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = LocalDimensions.current.eight)
            )

            Spacer(modifier = Modifier.height(LocalDimensions.current.four))

            Row(
                modifier = Modifier.padding(
                    start = LocalDimensions.current.eight,
                    end = LocalDimensions.current.eight
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AnnotatedString(
                        text = "$${product.price}",
                        spanStyles = listOf(
                            AnnotatedString.Range(
                                item = SpanStyle(
                                    color = LocalColors.current.red.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.Light,
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                start = 0,
                                end = "$${product.price}".length
                            )
                        )
                    ),
                    fontFamily = displayFontFamily,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.width(LocalDimensions.current.eight))

                Text(
                    text = "$${product.salePrice}",
                    fontFamily = displayFontFamily,
                    style = MaterialTheme.typography.bodyLarge,
                    color = LocalColors.current.black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(LocalDimensions.current.four))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = LocalDimensions.current.eight)
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
    }
}

@Composable
fun ProductList(
    uiState: HomeContract.UiState,
    onFavoriteClick: (ProductUi) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
) {
    LazyRow {
        items(uiState.productList) { product ->
            ProductCard(
                product = product,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(LocalDimensions.current.eight),
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}
