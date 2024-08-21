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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.source.local.ProductEntity
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.ECEmptyScreen
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun CartScreen(
    uiState: CartContract.UiState,
    onAction: (CartContract.UiAction) -> Unit,
    uiEffect: Flow<CartContract.UiEffect>,
    onNavigatePayment: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is CartContract.UiEffect.PaymentClick -> onNavigatePayment()
                    is CartContract.UiEffect.ShowToast -> TODO()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Cart",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        CartItemList(
            cartProductList = uiState.cartProductList,
            modifier = Modifier.weight(1f),
            onDeleteCartClick = { onAction(CartContract.UiAction.DeleteProductFromCart(it)) },
            increaseQuantity = { onAction(CartContract.UiAction.IncreaseQuantity(it)) },
            decreaseQuantity = { onAction(CartContract.UiAction.DecreaseQuantity(it)) }
        )
        if (uiState.cartProductList.isNotEmpty()) {
            CartFooter(
                uiState = uiState,
                onDiscountCodeChange = { /* Handle discount code change */ },
                onApplyDiscount = { /* Handle discount application */ },
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

@Composable
fun CartItemList(
    cartProductList: List<ProductEntity>,
    modifier: Modifier = Modifier,
    onDeleteCartClick: (Int) -> Unit,
    increaseQuantity: (Int) -> Unit,
    decreaseQuantity: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(cartProductList) { product ->
            CartItem(
                modifier = Modifier.fillMaxWidth(),
                uiState = CartContract.UiState(product = product),
                increaseQuantity = { increaseQuantity(it) },
                decreaseQuantity = { decreaseQuantity(it) },
                deleteProductFromCart = { onDeleteCartClick(it) },
            )
        }
    }
}

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    uiState: CartContract.UiState,
    deleteProductFromCart: (Int) -> Unit,
    increaseQuantity: (Int) -> Unit,
    decreaseQuantity: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LocalDimensions.current.eight)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(LocalDimensions.current.eight),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            uiState.product?.let { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = uiState.product.imageOne),
                        contentDescription = null,
                        modifier = Modifier.size(72.dp)
                    )

                    Spacer(modifier = Modifier.width(LocalDimensions.current.sixteen))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = uiState.product.title,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "$${"%.2f".format(product.price * product.quantity)}",
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        IconButton(
                            onClick = { deleteProductFromCart(product.productId) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Remove Item",
                                tint = Color.Red
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(
                                    onClick = { decreaseQuantity(product.productId) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Decrease Quantity",
                                        tint = Color.DarkGray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = "${product.quantity}",
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                IconButton(
                                    onClick = { increaseQuantity(product.productId) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowUp,
                                        contentDescription = "Increase Quantity",
                                        tint = Color.DarkGray,
                                        modifier = Modifier.size(16.dp)
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
            .padding(vertical = LocalDimensions.current.eight)
    ) {
        OutlinedTextField(
            value = uiState.discountCode,
            onValueChange = onDiscountCodeChange,
            label = {
                if (uiState.discountMessage.isNotEmpty()) {
                    Text(
                        text = uiState.discountMessage,
                        color = uiState.discountMessageColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text("Enter Discount Code")
                }
            },
            trailingIcon = {
                Button(
                    modifier = Modifier.padding(end = LocalDimensions.current.eight),
                    onClick = onApplyDiscount,
                    enabled = uiState.discountCode.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.discountCode.isNotEmpty()) LocalColors.current.primary else Color.Gray
                    ),
                    shape = RoundedCornerShape(16)
                ) {
                    Text("Apply")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Count:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${uiState.totalCartCount}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(LocalDimensions.current.four))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Price:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "%.2f".format(uiState.totalCartPrice),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(LocalDimensions.current.sixteen))

        CustomButton(text = "Payment", onClick = { onPaymentClick() })
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