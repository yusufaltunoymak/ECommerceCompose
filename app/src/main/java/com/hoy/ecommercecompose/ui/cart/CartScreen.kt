package com.hoy.ecommercecompose.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.hoy.ecommercecompose.data.source.remote.model.Product
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: CartUiState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "My Cart",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Cart items
        Column(
            modifier = Modifier
                .weight(1f)  // Allow this Column to take up available space
                .verticalScroll(rememberScrollState())
        ) {
            uiState.cartProductList.forEach { product ->
                CartItem(
                    product = product,
                    onQuantityChange = { /* Handle quantity change */ },
                    onRemoveItem = { /* Handle item removal */ }
                )
            }
        }

        // Footer at the bottom
        CartFooter(
            discountCode = uiState.discountCode,
            total = uiState.totalCartPrice,
            onDiscountCodeChange = { /* Handle discount code change */ },
            onApplyDiscount = { /* Handle discount application */ },
            onCheckout = { /* Handle checkout */ }
        )
    }
}


@Composable
fun CartItem(
    product: Product,
    onQuantityChange: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.imageOne),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.title ?: "Product Title", color = Color.Gray)
            Text(text = "$${product.price}", color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (product.count ?: 0 > 1) onQuantityChange((product.count ?: 1) - 1)
            }) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Decrease Quantity",
                    tint = LocalColors.current.primary
                )
            }
            Text(text = "${product.count ?: 1}")
            IconButton(onClick = { onQuantityChange((product.count ?: 1) + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
            }
        }
        IconButton(onClick = onRemoveItem) {
            Icon(Icons.Default.Delete, contentDescription = "Remove Item", tint = Color.Red)
        }
    }
}

@Composable
fun CartFooter(
    discountCode: String,
    total: Double,
    onDiscountCodeChange: (String) -> Unit,
    onApplyDiscount: () -> Unit,
    onCheckout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = discountCode,
            onValueChange = onDiscountCodeChange,
            label = { Text("Enter Discount Code") },
            trailingIcon = {
                Button(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    onClick = onApplyDiscount,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    shape = RoundedCornerShape(16)
                ) {
                    Text("Apply")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: $${total}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(text = "Checkout", onClick = { /*TODO*/ })

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    CartScreen(
        uiState = CartUiState(
            cartProductList = listOf(
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
                Product(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "https://via.placeholder.com/64",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "https://via.placeholder.com/64",
                    imageThree = "https://via.placeholder.com/64",
                    saleState = false
                ),
            ),
            totalCartCount = 7,
            totalCartPrice = 70.00
        ),
        navController = rememberNavController()
    )
}