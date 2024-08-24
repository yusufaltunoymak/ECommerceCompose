package com.hoy.ecommercecompose.ui.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.hoy.ecommercecompose.common.formatDateWithTime
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun OrderScreen(
    uiState: OrderContract.UiState,
    onAction: (OrderContract.UiAction) -> Unit,
    uiEffect: Flow<OrderContract.UiEffect>,
    navigateBack: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is OrderContract.UiEffect.ShowError -> {
                    }
                    is OrderContract.UiEffect.NavigateBack -> navigateBack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            OrderTopBar(onBackClick = navigateBack)
        },
        content = { paddingValues ->
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                OrderList(
                    orders = uiState.orders,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(text = "Orders")
        }
    )
}

@Composable
fun OrderList(orders: List<PaymentEntity>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(orders) { order ->
            OrderCard(paymentEntity = order)
        }
    }
}

@Composable
fun OrderCard(paymentEntity: PaymentEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Card Number: ${paymentEntity.cardNumber}")
            Text(text = "Card Holder Name: ${paymentEntity.cardHolderName}")
            Text(text = "Expiration Date: ${paymentEntity.expirationDate}")
            Text(text = "City: ${paymentEntity.city}")
            Text(text = "District: ${paymentEntity.district}")
            Text(text = "Full Address: ${paymentEntity.fullAddress}")
            Text(text = "Product ID: ${paymentEntity.productId}")
            Text(text = "Quantity: ${paymentEntity.quantity}")
            Text(text = "Price: ${paymentEntity.price}")
            Text(text = "Order Date: ${paymentEntity.orderDate.formatDateWithTime()}")
        }

        AsyncImage(model = paymentEntity.imageOne, contentDescription ="Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(100.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    OrderScreen(
        uiState = OrderContract.UiState(),
        uiEffect = flowOf(),
        onAction = {},
        navigateBack = {})
}
