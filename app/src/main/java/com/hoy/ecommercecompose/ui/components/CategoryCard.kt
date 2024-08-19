package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.ui.home.HomeContract
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun CategoryCard(category: Category, modifier: Modifier = Modifier, onCategoryListClick: (String) -> Unit) {
    Card(
        modifier = modifier
            .clickable { onCategoryListClick(category.name) }
            .size(100.dp, 120.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(category.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = "Category Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp, 80.dp)
                    .background(Color.White)
                    .clip(RoundedCornerShape(2.dp))
            )
            Text(
                text = category.name,
                color = Color.Black,
                fontFamily = displayFontFamily,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CategoryList(
    uiState: HomeContract.UiState,
    modifier: Modifier = Modifier,
    onCategoryListClick: (String) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(uiState.categoryList) { category ->
            CategoryCard(
                onCategoryListClick = onCategoryListClick,
                category = category,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
