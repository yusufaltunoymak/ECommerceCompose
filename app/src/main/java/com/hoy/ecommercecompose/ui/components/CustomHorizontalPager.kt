package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomHorizontalPager(
    imageUrls: List<Any>,
    pageCount: Int = imageUrls.size,
    contentDescription: (Int) -> String = { "Image $it" },
    modifier: Modifier = Modifier,
    activeIndicatorColor: Color = Color.DarkGray,
    inactiveIndicatorColor: Color = Color.White,
    height: Int = 200,
    clip: Int = 16,
    padding: Int = 8
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val pagerState = rememberPagerState(pageCount = { pageCount })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
        ) {
            androidx.compose.foundation.pager.HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding.dp)
                ) {
                    val painter = rememberAsyncImagePainter(model = imageUrls[page])
                    Image(
                        painter = painter,
                        contentDescription = contentDescription(page),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(clip.dp))
                    )
                }
            }

            val currentPage by remember {
                derivedStateOf { pagerState.currentPage }
            }

            DotIndicator(
                pageCount = pageCount,
                currentPage = currentPage,
                activeColor = activeIndicatorColor,
                inactiveColor = inactiveIndicatorColor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
fun DotIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.DarkGray,
    inactiveColor: Color = Color.White
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        repeat(pageCount) { index ->
            val color = if (index == currentPage) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, shape = CircleShape)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}