package com.hoy.ecommercecompose.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    navController: NavController,
    viewModel : HomeViewModel
) {
    val scrollState = rememberScrollState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        with(uiState) {
            if (currentUser != null) {
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
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray,
            fontFamily = displayFontFamily
        )
            CategoryList(uiState)

        Text(
            text = "Top Rated Products",
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray,
            fontFamily = displayFontFamily
        )
        ProductGrid(uiState = uiState, onFavoriteClick = { product ->
            val updatedProduct = product.copy(isFavorite = !product.isFavorite)
            viewModel.addToFavorites(userId, updatedProduct.id)
        })

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
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

@Composable
fun CategoryCard(category: Category, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(category.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = "Category Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = category.name,
                fontFamily = displayFontFamily,
                modifier = modifier
                    .align(Alignment.CenterVertically),
            )

        }
    }
}

@Composable
fun CategoryList(uiState: HomeUiState, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(uiState.categoryList) { category ->
            CategoryCard(
                category = category,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ProductCard(
    product: ProductUi,
    modifier: Modifier = Modifier,
    onFavoriteClick: (ProductUi) -> Unit,
    ) {
    val iconColor = if (product.isFavorite) Color(0xFFFFA500) else Color.Gray
    Card(
        modifier = modifier
            .size(170.dp, 260.dp)
            .clip(RoundedCornerShape(8.dp)),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(top = 8.dp)
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
                    modifier = Modifier.size(150.dp)
                )

                IconButton(
                    onClick = { onFavoriteClick(product) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(color = Color.White, shape = CircleShape)
                        .padding(4.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = iconColor,
                        modifier = modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
                fontFamily = displayFontFamily,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${product.price}",
                fontFamily = displayFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
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
fun ProductGrid(uiState: HomeUiState, onFavoriteClick: (ProductUi) -> Unit) {
    LazyRow {
        items(uiState.productList) { product ->
            ProductCard(
                product = product,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview() {
//    HomeScreen(
//        uiState = HomeUiState(
//            currentUser = User("John Doe"),
//            productList = listOf(
//                ProductUi(
//                    category = "Electronics",
//                    count = 10,
//                    description = "Description",
//                    id = 1,
//                    imageOne = "https://via.placeholder.com/150",
//                    price = 100.0,
//                    title = "Product1",
//                    isFavorite = false,
//                    rate = 4.5
//                )
//                )
//        ),
//        navController = rememberNavController()
//    )
//}
