package com.hoy.ecommercecompose.ui.home

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.model.User
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun HomeScreen(modifier: Modifier = Modifier, uiState: HomeUiState) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        with(uiState) {
            if(currentUser != null) {
                Text(
                    text = "Welcome ${currentUser.name}!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    fontFamily = displayFontFamily
                )
            }
        }

        CustomSearchView(
            text = "",
            onTextChange = { /*TODO*/ },
            placeHolder = "Search for products",
            onCloseClicked = { /*TODO*/ }) {
        }

        HorizontalPager()

        Text(
            text = "Categories",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.DarkGray,
            fontFamily = displayFontFamily
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPager() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val pageCount by remember {
            mutableIntStateOf(3)
        }

        val pagerState = rememberPagerState(pageCount = { pageCount })
        val imageUrls = listOf(
            R.drawable.sale,
            R.drawable.sale2,
            R.drawable.sale
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val painter = rememberAsyncImagePainter(model = imageUrls[page])
                    Image(
                        painter = painter,
                        contentDescription = "Image $page",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }

            val currentPage by remember {
                derivedStateOf { pagerState.currentPage }
            }

            DotIndicator(
                pageCount = pageCount,
                currentPage = currentPage,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    HomeScreen(
        uiState = HomeUiState(
            currentUser = User("John Doe")
        )
    )
}
