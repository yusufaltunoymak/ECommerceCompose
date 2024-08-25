package com.hoy.ecommercecompose.ui.order

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.formatDateWithTime
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import com.hoy.ecommercecompose.ui.theme.ECTheme
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

    OrderList(paymentList = uiState.orders)
}

@Composable
fun OrderList(
    paymentList: List<PaymentEntity>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(ECTheme.dimensions.eight)
    ) {
        items(paymentList) { paymentEntity ->
            OrderCard(paymentEntity = paymentEntity)
        }
    }
}

@Composable
fun OrderCard(paymentEntity: PaymentEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ECTheme.dimensions.four)
            .clip(RoundedCornerShape(ECTheme.dimensions.twelve)),
        border = BorderStroke(ECTheme.dimensions.one, ECTheme.colors.gray),
        colors = CardDefaults.cardColors(
            containerColor = ECTheme.colors.white
        )
    ) {
        var expanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(ECTheme.dimensions.eight)
        ) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(paymentEntity.imageOne)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.product_image),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(ECTheme.dimensions.fifty)
                        .padding(ECTheme.dimensions.four)
                )
                Column {
                    Text(text = paymentEntity.title)
                    Text(text = paymentEntity.orderDate.formatDateWithTime())
                }
                Spacer(modifier = Modifier.weight(1f))
                OrderItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))
                OrderDetails(paymentEntity = paymentEntity)
            }
        }
    }
}

@Composable
fun OrderItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.app_name),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun OrderDetails(paymentEntity: PaymentEntity) {
    Column {
        Text(text = stringResource(id = R.string.card_number_string, paymentEntity.cardNumber))
        Text(
            text = stringResource(
                id = R.string.card_holder_name_string,
                paymentEntity.cardHolderName
            )
        )
        Text(text = stringResource(id = R.string.city, paymentEntity.city))
        Text(text = stringResource(id = R.string.district, paymentEntity.district))
        Text(text = stringResource(id = R.string.full_address, paymentEntity.fullAddress))
        Text(text = stringResource(id = R.string.price, paymentEntity.price))
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
