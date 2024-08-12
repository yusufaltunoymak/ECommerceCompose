package com.hoy.ecommercecompose.ui.search

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CustomSearchView

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        // Load all products initially
        viewModel.loadAllProducts()
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CustomSearchView(
            text = searchQuery,
            onTextChange = { query ->
                searchQuery = query
                viewModel.searchProducts(query)
            },
            placeHolder = "Search for products",
            onCloseClicked = {
                searchQuery = ""
                viewModel.loadAllProducts()
            },
            onSearchClick = {
                // Handle search button click if needed
            },
            onSortClick = {
                searchQuery = ""
                viewModel.loadAllProducts()
            },
            modifier = Modifier.focusRequester(focusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchUiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (!searchUiState.errorMessage.isNullOrEmpty()) {
            Text(
                text = searchUiState.errorMessage.orEmpty(),
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            ProductList(
                productList = searchUiState.productList,
                navController = navController,
                onClick = onClick

            )
        }
    }
}


@Composable
fun ProductList(
    productList: List<ProductUi>,
    navController: NavController,
    onClick: (Int) -> Unit
) {
    LazyColumn {
        items(productList) { product ->
            ProductListItem(
                product = product,
                onClick = onClick
            )
        }
    }
}

@Composable
fun ProductListItem(
    product: ProductUi,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(product.id) },
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

