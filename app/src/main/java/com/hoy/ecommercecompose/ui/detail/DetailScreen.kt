package com.hoy.ecommercecompose.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomHorizontalPager
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun ProductDetailScreen() {

    val images = listOf(
        R.drawable.log1,
        R.drawable.log2,
        R.drawable.log3
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                CustomHorizontalPager(
                    imageUrls = images,
                    height = 300,
                    clip = 0,
                    padding = 0
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = LocalColors.current.primary
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
                        .background(color = Color.Cyan)
                        .fillMaxSize()
                        .padding(16.dp)
                        .clip(
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                ) {
                    Text(
                        text = "Wireless Headphone",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "$520.00",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(34.dp))
                            .width(
                                IntrinsicSize.Min
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .background(color = LocalColors.current.primary)
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "4.8")
                        }
                    }
                    Text(text = "(320 Reviews)", color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Detail",
                        color = LocalColors.current.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Pellentesque faucibus elementum nisi eu eleifend. Aenean ultrices et massa nec consectetur. Donec venenatis nec metus auctor rhoncus. Nam vehicula eros id quam tincidunt, a finibus quam malesuada. Nulla et ante ullamcorper leo aliquet varius. ",
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
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
                                    .border(BorderStroke(1.dp, Color.White), shape = CircleShape)
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
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prew() {
    ProductDetailScreen()
}