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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hoy.ecommercecompose.common.orEmpty
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.bodyFontFamily

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.productDetail != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

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
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = LocalColors.current.primary
                            )
                        }

                        Row {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    tint = LocalColors.current.primary
                                )
                            }
                            IconButton(onClick = { viewModel.toggleFavorite() }) {
                                Icon(
                                    imageVector = if (uiState.productDetail?.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = if (uiState.productDetail?.isFavorite == true) LocalColors.current.primary else LocalColors.current.primary
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier.clip(
                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxSize()
                            .padding(16.dp)
                            .clip(
                                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                    ) {
                        uiState.productDetail?.title?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }
                        Text(
                            text = "$${uiState.productDetail?.price}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        RatingBar(rating = uiState.productDetail?.rate.orEmpty())

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Detail",
                            color = LocalColors.current.primary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                        Text(
                            text = "${uiState.productDetail?.description}",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = bodyFontFamily
                        )

                        Spacer(modifier = Modifier.height(16.dp))

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
                                    .clip(RoundedCornerShape(34.dp))
                                    .background(color = Color.Black)
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .border(
                                            BorderStroke(1.dp, Color.White),
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape)
                                ) {
                                    IconButton(onClick = { /* decrease quantity */ }) {
                                        Icon(
                                            Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Text(text = "1", style = TextStyle(color = Color.White))
                                    IconButton(onClick = { /* increase quantity */ }) {
                                        Icon(
                                            Icons.Default.KeyboardArrowUp,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(30.dp))
                                Button(
                                    onClick = { },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = LocalColors.current.primary
                                    )
                                ) {
                                    Text(
                                        text = "Add to Cart", modifier = Modifier.padding(6.dp),
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.height(30.dp))
                        }

                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Product not found")
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
                contentDescription = null,
                tint = starsColor,
                modifier = modifier
            )
        }
        Text(
            text = "$rating",
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = bodyFontFamily,
            modifier = modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ImageList(modifier: Modifier = Modifier, uiState: DetailUiState) {
    uiState.productDetail?.let { detail ->
        CustomHorizontalPager(
            imageUrls = detail.getImageList().filterNotNull(),
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
}