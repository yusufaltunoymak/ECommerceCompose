package com.hoy.ecommercecompose.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.common.noRippleClickable
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.ui.components.CustomSearchView
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchScreen(
    uiEffect: Flow<SearchContract.UiEffect>,
    uiState: SearchContract.UiState,
    onAction: (SearchContract.UiAction) -> Unit,
    onDetailClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is SearchContract.UiEffect.GoToDetail -> {
                onDetailClick(effect.productId)
            }

            is SearchContract.UiEffect.NavigateBack -> {
                onBackClick()
            }
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ECTheme.dimensions.sixteen)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier
                    .size(ECTheme.dimensions.fiftySix)
                    .border(
                        BorderStroke(
                            ECTheme.dimensions.two,
                            ECTheme.colors.primary.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(ECTheme.dimensions.twelve)
                    )
            ) {
                Icon(
                    modifier = Modifier.size(ECTheme.dimensions.thirtyEight),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(ECTheme.dimensions.eight))

            CustomSearchView(
                text = searchQuery,
                onTextChange = { query ->
                    searchQuery = query
                    onAction(SearchContract.UiAction.ChangeQuery(query))
                },
                placeHolder = stringResource(R.string.search_products),
                onCloseClicked = {
                    searchQuery = ""
                    onAction(SearchContract.UiAction.LoadProduct(uiState.productList))
                },
                onSearchClick = {
                    // Handle search button click if needed
                },
                modifier = Modifier.focusRequester(focusRequester)
            )
        }

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (!uiState.errorMessage.isNullOrEmpty()) {
            Text(
                text = uiState.errorMessage,
                color = ECTheme.colors.red,
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
            .padding(vertical = ECTheme.dimensions.eight)
            .noRippleClickable { onDetailClick(product.id) },
        colors = CardDefaults.cardColors(
            containerColor = ECTheme.colors.white
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = ECTheme.dimensions.two)
    ) {
        Row(
            modifier = Modifier.padding(ECTheme.dimensions.sixteen),
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
