package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.home.HomeContract
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun ProductCard(
    product: ProductUi,
    modifier: Modifier = Modifier,
    onFavoriteClick: (ProductUi) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val iconColor = if (product.isFavorite) LocalColors.current.primary else Color.Gray
    Card(
        modifier = modifier
            .size(170.dp, 280.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onNavigateToDetail(product.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp) // Use elevation for shadow
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(product.imageOne)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Product Image",
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )

                IconButton(
                    onClick = { onFavoriteClick(product) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp) // Add padding to top and end
                        .background(color = Color.LightGray, shape = CircleShape)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = iconColor,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = displayFontFamily,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = AnnotatedString(
                    text = "$${product.price}",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            item = SpanStyle(
                                color = Color.Red.copy(alpha = 0.6f), // Lighter color
                                fontWeight = FontWeight.Light,
                                textDecoration = TextDecoration.LineThrough
                            ),
                            start = 0,
                            end = "$${product.price}".length
                        )
                    )
                ),
                fontFamily = displayFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${product.salePrice}",
                fontFamily = displayFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = LocalColors.current.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.rate.toString(),
                    fontFamily = displayFontFamily,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ProductList(
    uiState: HomeContract.HomeUiState,
    onFavoriteClick: (ProductUi) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
) {
    LazyRow {
        items(uiState.productList) { product ->
            ProductCard(
                product = product,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(8.dp),
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}