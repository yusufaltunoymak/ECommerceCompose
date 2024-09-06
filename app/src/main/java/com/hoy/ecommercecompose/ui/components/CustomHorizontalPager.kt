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
import coil.compose.rememberAsyncImagePainter
import com.hoy.ecommercecompose.ui.theme.ECTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomHorizontalPager(
    modifier: Modifier = Modifier,
    imageUrls: List<Any>,
    pageCount: Int = imageUrls.size,
    contentDescription: (Int) -> String = { "Image $it" },
    activeIndicatorColor: Color = ECTheme.colors.primary,
    inactiveIndicatorColor: Color = ECTheme.colors.darkGray,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(ECTheme.dimensions.twoHundred),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val pagerState = rememberPagerState(pageCount = { pageCount })

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            androidx.compose.foundation.pager.HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val painter = rememberAsyncImagePainter(model = imageUrls[page])
                    Image(
                        painter = painter,
                        contentDescription = contentDescription(page),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(ECTheme.dimensions.sixteen))
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
                    .padding(bottom = ECTheme.dimensions.twelve)
            )
        }
    }
}

@Composable
fun DotIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = ECTheme.colors.primary,
    inactiveColor: Color = ECTheme.colors.darkGray
) {
    Row(
        modifier = modifier.padding(ECTheme.dimensions.eight),
        horizontalArrangement = Arrangement.spacedBy(
            ECTheme.dimensions.eight,
            Alignment.CenterHorizontally
        )
    ) {
        repeat(pageCount) { index ->
            val color = if (index == currentPage) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(ECTheme.dimensions.eight)
                    .background(color, shape = CircleShape)
                    .padding(horizontal = ECTheme.dimensions.sixteen)
            )
        }
    }
}
