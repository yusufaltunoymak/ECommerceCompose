package com.hoy.ecommercecompose.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.orEmpty
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.theme.ECTheme
import com.hoy.ecommercecompose.ui.theme.bodyFontFamily
import kotlinx.coroutines.flow.Flow

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailContract.UiState,
    onAction: (ProductDetailContract.UiAction) -> Unit,
    uiEffect: Flow<ProductDetailContract.UiEffect>,
    onBackClick: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is ProductDetailContract.UiEffect.BackScreen -> onBackClick()
                    is ProductDetailContract.UiEffect.ShowError -> TODO()
                    is ProductDetailContract.UiEffect.ShowToastMessage -> {}
                    ProductDetailContract.UiEffect.NavigateBack -> TODO()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.productDetail != null -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Box {
                            ImageList(
                                modifier = Modifier.fillMaxWidth(),
                                uiState = uiState
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                IconButton(onClick = { onBackClick() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.back_icon),
                                        tint = ECTheme.colors.primary
                                    )
                                }

                                Row {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = stringResource(id = R.string.share),
                                            tint = ECTheme.colors.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = { onAction(ProductDetailContract.UiAction.ToggleFavoriteClick) }
                                    ) {
                                        Icon(
                                            imageVector = if (uiState.productDetail.isFavorite == true) {
                                                Icons.Default.Favorite
                                            } else {
                                                Icons.Default.FavoriteBorder
                                            },
                                            contentDescription = stringResource(id = R.string.favorite),
                                            tint = ECTheme.colors.primary
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier.clip(
                                RoundedCornerShape(
                                    topStart = ECTheme.dimensions.sixteen,
                                    topEnd = ECTheme.dimensions.sixteen
                                )
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(color = ECTheme.colors.white)
                                    .fillMaxSize()
                                    .padding(ECTheme.dimensions.sixteen)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = ECTheme.dimensions.sixteen,
                                            topEnd = ECTheme.dimensions.sixteen
                                        )
                                    )
                            ) {
                                uiState.productDetail.title?.let {
                                    Text(
                                        text = it,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = ECTheme.typography.extraLarge
                                    )
                                }
                                Text(
                                    text = "$${uiState.productDetail.price}",
                                    fontSize = ECTheme.typography.large,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                )

                                Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

                                RatingBar(rating = uiState.productDetail.rate.orEmpty())

                                Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

                                Text(
                                    text = "Detail",
                                    color = ECTheme.colors.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = ECTheme.typography.large
                                )

                                Text(
                                    text = "${uiState.productDetail.description}",
                                    fontSize = ECTheme.typography.body,
                                    color = ECTheme.colors.black,
                                    fontFamily = bodyFontFamily
                                )

                                Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(ECTheme.dimensions.thirtyFour))
                                            .background(color = ECTheme.colors.black)
                                            .padding(
                                                horizontal = ECTheme.dimensions.four,
                                                vertical = ECTheme.dimensions.two
                                            )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .border(
                                                    BorderStroke(
                                                        ECTheme.dimensions.one,
                                                        ECTheme.colors.white
                                                    ),
                                                    shape = CircleShape
                                                )
                                                .clip(CircleShape)
                                        ) {
                                            IconButton(onClick = { /* decrease quantity */ }) {
                                                Icon(
                                                    Icons.Default.KeyboardArrowDown,
                                                    contentDescription = null,
                                                    tint = ECTheme.colors.white
                                                )
                                            }
                                            Text(
                                                text = stringResource(id = R.string.one_text),
                                                style = TextStyle(color = ECTheme.colors.white)
                                            )
                                            IconButton(onClick = { /* increase quantity */ }) {
                                                Icon(
                                                    Icons.Default.KeyboardArrowUp,
                                                    contentDescription = null,
                                                    tint = ECTheme.colors.white
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(ECTheme.dimensions.thirty))
                                        Button(
                                            onClick = {
                                                onAction(
                                                    ProductDetailContract.UiAction.AddToCartClick(
                                                        uiState.productDetail
                                                    )
                                                )
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = ECTheme.colors.primary
                                            )
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.add_to_cart),
                                                modifier = Modifier.padding(ECTheme.dimensions.eight),
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(ECTheme.dimensions.thirty))
                                }
                            }
                        }
                    }
                }
            }

            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage)
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = ECTheme.colors.primary
) {
    var isHalfStar = (rating % 1) != 0.0

    Row {
        for (index in 1..stars) {
            Icon(
                imageVector =
                if (index <= rating) {
                    Icons.Rounded.Star
                } else {
                    if (isHalfStar) {
                        isHalfStar = false
                        Icons.AutoMirrored.Rounded.StarHalf
                    } else {
                        Icons.Rounded.StarOutline
                    }
                },
                contentDescription = stringResource(id = R.string.rating),
                tint = starsColor,
                modifier = modifier
            )
        }
        Text(
            text = "$rating",
            color = ECTheme.colors.black,
            fontSize = ECTheme.typography.body,
            fontFamily = bodyFontFamily,
            modifier = modifier
                .padding(start = ECTheme.dimensions.four)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ImageList(modifier: Modifier = Modifier, uiState: ProductDetailContract.UiState) {
    uiState.productDetail?.let { detail ->
        CustomHorizontalPager(
            imageUrls = detail.getImageList().filterNotNull(),
            modifier = modifier
                .fillMaxWidth()
                .height(ECTheme.dimensions.threeHundred)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
}
