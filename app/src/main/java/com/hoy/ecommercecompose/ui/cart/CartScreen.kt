package com.hoy.ecommercecompose.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.ECEmptyScreen
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    uiState: CartContract.UiState,
    onAction: (CartContract.UiAction) -> Unit,
    uiEffect: Flow<CartContract.UiEffect>,
    onNavigatePayment: () -> Unit,
) {
    val snackBarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is CartContract.UiEffect.PaymentClick -> onNavigatePayment()
            is CartContract.UiEffect.ShowDeleteConfirmation -> {
                val result = snackBarHostState.showSnackbar(
                    message = "Do you want to delete this item?",
                    actionLabel = "Yes"
                )
                if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                    onAction(CartContract.UiAction.DeleteProductFromCart(effect.id))
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.my_cart)) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = ECTheme.dimensions.sixteen)
            ) {
                CartItemList(
                    cartProductList = uiState.cartProductList,
                    modifier = Modifier.weight(1f),
                    onAction = onAction
                )
                if (uiState.cartProductList.isNotEmpty()) {
                    CartFooter(
                        uiState = uiState,
                        onDiscountCodeChange = {
                            onAction(
                                CartContract.UiAction.OnDiscountCodeChange(
                                    it
                                )
                            )
                        },
                        onApplyDiscount = { onAction(CartContract.UiAction.ApplyDiscount) },
                        onPaymentClick = { onNavigatePayment() }
                    )
                } else {
                    ECEmptyScreen(
                        title = R.string.empty_cart_title,
                        description = R.string.empty_cart_desc,
                        icon = R.drawable.ic_cart
                    )
                }
            }
        }
    )
}

@Composable
fun CartItemList(
    cartProductList: List<ProductEntity>,
    modifier: Modifier = Modifier,
    onAction: (CartContract.UiAction) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(cartProductList) { product ->
            CartItem(
                modifier = Modifier.fillMaxWidth(),
                uiState = CartContract.UiState(product = product),
                onAction = onAction
            )
        }
    }
}

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    uiState: CartContract.UiState,
    onAction: (CartContract.UiAction) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ECTheme.dimensions.four),
            shape = RoundedCornerShape(ECTheme.dimensions.eight),
            elevation = CardDefaults.cardElevation(ECTheme.dimensions.four),
            colors = CardDefaults.cardColors(containerColor = ECTheme.colors.white)
        ) {
            uiState.product?.let { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ECTheme.dimensions.eight),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = uiState.product.imageOne),
                        contentDescription = null,
                        modifier = Modifier.size(ECTheme.dimensions.seventyTwo)
                    )

                    Spacer(modifier = Modifier.width(ECTheme.dimensions.sixteen))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = uiState.product.title,
                            color = ECTheme.colors.gray,
                            modifier = Modifier.padding(bottom = ECTheme.dimensions.eight)
                        )

                        Text(
                            text = "$${"%.2f".format(product.price * product.quantity)}",
                            color = ECTheme.colors.gray
                        )
                    }

                    Spacer(modifier = Modifier.width(ECTheme.dimensions.sixteen))

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = ECTheme.colors.white,
                                    shape = RoundedCornerShape(ECTheme.dimensions.eight)
                                )
                                .padding(
                                    horizontal = ECTheme.dimensions.eight,
                                    vertical = ECTheme.dimensions.four
                                )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(
                                    onClick = {
                                        if (product.quantity == 1) {
                                            onAction(
                                                CartContract.UiAction.ShowDeleteConfirmation(
                                                    product.productId
                                                )
                                            )
                                        } else {
                                            onAction(CartContract.UiAction.DecreaseQuantity(product.productId))
                                        }
                                    },
                                    modifier = Modifier.size(ECTheme.dimensions.twentyFour)
                                ) {
                                    Icon(
                                        imageVector = if (product.quantity == 1) Icons.Default.Delete else Icons.Default.KeyboardArrowDown,
                                        contentDescription = stringResource(id = R.string.decrease_quantity),
                                        tint = ECTheme.colors.darkGray,
                                        modifier = Modifier.size(ECTheme.dimensions.sixteen)
                                    )
                                }
                                Text(
                                    text = "${product.quantity}",
                                    color = ECTheme.colors.darkGray,
                                    modifier = Modifier.padding(horizontal = ECTheme.dimensions.four)
                                )
                                IconButton(
                                    onClick = {
                                        onAction(
                                            CartContract.UiAction.IncreaseQuantity(
                                                product.productId
                                            )
                                        )
                                    },
                                    modifier = Modifier.size(ECTheme.dimensions.twentyFour)
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowUp,
                                        contentDescription = stringResource(id = R.string.increase_quantity),
                                        tint = ECTheme.colors.darkGray,
                                        modifier = Modifier.size(ECTheme.dimensions.sixteen)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartFooter(
    uiState: CartContract.UiState,
    onDiscountCodeChange: (String) -> Unit,
    onApplyDiscount: () -> Unit,
    onPaymentClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = ECTheme.dimensions.eight)
    ) {
        OutlinedTextField(
            value = uiState.discountCode,
            onValueChange = onDiscountCodeChange,
            label = { Text("Enter Discount Code") },
            trailingIcon = {
                Button(
                    modifier = Modifier.padding(end = ECTheme.dimensions.eight),
                    onClick = onApplyDiscount,
                    enabled = uiState.discountCode.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.discountCode.isNotEmpty()) ECTheme.colors.primary else ECTheme.colors.gray
                    ),
                    shape = RoundedCornerShape(ECTheme.dimensions.sixteen)
                ) {
                    Text(stringResource(id = R.string.apply))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (uiState.discountMessage.isNotEmpty()) {
            Text(
                text = uiState.discountMessage,
                color = uiState.discountMessageColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = ECTheme.dimensions.four)
            )
        }

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.total_count),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${uiState.totalCartCount}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(ECTheme.dimensions.four))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.total_price),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "%.2f".format(uiState.totalCartPrice),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        CustomButton(text = stringResource(id = R.string.payment), onClick = { onPaymentClick() })
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(
        uiState = CartContract.UiState(),
        onAction = {},
        uiEffect = flow { },
        onNavigatePayment = {}
    )
}
