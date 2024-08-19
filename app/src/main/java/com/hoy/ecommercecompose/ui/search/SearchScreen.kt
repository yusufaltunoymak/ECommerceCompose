package com.hoy.ecommercecompose.ui.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchScreen(
    uiEffect: Flow<SearchContract.UiEffect>,
    uiState: SearchContract.UiState,
    onAction: (SearchContract.UiAction) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SearchContract.UiEffect.GoToDetail -> {
                        onDetailClick(effect.productId)
                    }
                }
            }
        }
    }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    Log.d("SearchScreen", "SearchScreen Composable")
    LaunchedEffect(Unit) {
        Log.d("SearchScreen", "Focus requested")
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CustomSearchView(
            text = searchQuery,
            onTextChange = { query ->
                searchQuery = query
                onAction(SearchContract.UiAction.ChangeQuery(query))
            },
            placeHolder = "Search for products",
            onCloseClicked = {
                searchQuery = ""
                onAction(SearchContract.UiAction.LoadProduct(uiState.productList))
            },
            onSearchClick = {
                // Handle search button click if needed
            },
            onSortClick = {
                searchQuery = ""
                onAction(SearchContract.UiAction.LoadProduct(uiState.productList))
            },
            modifier = Modifier.focusRequester(focusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (!uiState.errorMessage.isNullOrEmpty()) {
            Text(
                text = uiState.errorMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            ProductList(
                productList = uiState.productList,
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun ProductList(
    productList: List<ProductUi>,
    onDetailClick: (Int) -> Unit
) {
    LazyColumn {
        items(productList) { product ->
            ProductListItem(
                product = product,
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun ProductListItem(
    product: ProductUi,
    onDetailClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onDetailClick(product.id) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
