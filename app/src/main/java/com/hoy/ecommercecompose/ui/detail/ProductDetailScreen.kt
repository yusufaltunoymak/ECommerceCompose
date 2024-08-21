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
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.LocalFontSizes
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
                                        tint = LocalColors.current.primary
                                    )
                                }

                                Row {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = stringResource(id = R.string.share),
                                            tint = LocalColors.current.primary
                                        )
                                    }
                                    IconButton(onClick = { onAction(ProductDetailContract.UiAction.ToggleFavoriteClick) }) {
                                        Icon(
                                            imageVector = if (uiState.productDetail.isFavorite == true) {
                                                Icons.Default.Favorite
                                            } else {
                                                Icons.Default.FavoriteBorder
                                            },
                                            contentDescription = stringResource(id = R.string.favorite),
                                            tint = LocalColors.current.primary
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier.clip(
                                RoundedCornerShape(
                                    topStart = LocalDimensions.current.sixteen,
                                    topEnd = LocalDimensions.current.sixteen
                                )
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(color = LocalColors.current.white)
                                    .fillMaxSize()
                                    .padding(LocalDimensions.current.sixteen)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = LocalDimensions.current.sixteen,
                                            topEnd = LocalDimensions.current.sixteen
                                        )
                                    )
                            ) {
                                uiState.productDetail.title?.let {
                                    Text(
                                        text = it,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = LocalFontSizes.current.extraLarge
                                    )
                                }
                                Text(
                                    text = "$${uiState.productDetail.price}",
                                    fontSize = LocalFontSizes.current.large,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                )

                                Spacer(modifier = Modifier.height(LocalDimensions.current.eight))

                                RatingBar(rating = uiState.productDetail.rate.orEmpty())

                                Spacer(modifier = Modifier.height(LocalDimensions.current.sixteen))

                                Text(
                                    text = "Detail",
                                    color = LocalColors.current.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = LocalFontSizes.current.large
                                )

                                Text(
                                    text = "${uiState.productDetail.description}",
                                    fontSize = LocalFontSizes.current.body,
                                    color = LocalColors.current.black,
                                    fontFamily = bodyFontFamily
                                )

                                Spacer(modifier = Modifier.height(LocalDimensions.current.sixteen))

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
                                            .clip(RoundedCornerShape(LocalDimensions.current.thirtyFour))
                                            .background(color = LocalColors.current.black)
                                            .padding(
                                                horizontal = LocalDimensions.current.four,
                                                vertical = LocalDimensions.current.two
                                            )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .border(
                                                    BorderStroke(
                                                        LocalDimensions.current.one,
                                                        LocalColors.current.white
                                                    ),
                                                    shape = CircleShape
                                                )
                                                .clip(CircleShape)
                                        ) {
                                            IconButton(onClick = { /* decrease quantity */ }) {
                                                Icon(
                                                    Icons.Default.KeyboardArrowDown,
                                                    contentDescription = null,
                                                    tint = LocalColors.current.white
                                                )
                                            }
                                            Text(text = stringResource(id = R.string.one_text), style = TextStyle(color = LocalColors.current.white))
                                            IconButton(onClick = { /* increase quantity */ }) {
                                                Icon(
                                                    Icons.Default.KeyboardArrowUp,
                                                    contentDescription = null,
                                                    tint = LocalColors.current.white
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(LocalDimensions.current.thirty))
                                        Button(
                                            onClick = {
                                                onAction(
                                                    ProductDetailContract.UiAction.AddToCartClick(
                                                        uiState.productDetail
                                                    )
                                                )
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = LocalColors.current.primary
                                            )
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.add_to_cart),
                                                modifier = Modifier.padding(LocalDimensions.current.eight),
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(LocalDimensions.current.thirty))
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
    starsColor: Color = LocalColors.current.primary
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
            color = LocalColors.current.black,
            fontSize = LocalFontSizes.current.body,
            fontFamily = bodyFontFamily,
            modifier = modifier
                .padding(start = LocalDimensions.current.four)
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
                .height(LocalDimensions.current.threeHundred)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
}
