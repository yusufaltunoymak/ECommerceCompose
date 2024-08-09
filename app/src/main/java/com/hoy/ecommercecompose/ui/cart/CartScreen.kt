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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.source.remote.model.ProductDto
import com.hoy.ecommercecompose.ui.components.CustomButton

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

        CartItemList(
            uiState = uiState,
            modifier = Modifier.weight(1f)
        )

        // Footer at the bottom
        CartFooter(
            discountCode = uiState.discountCode,
            total = uiState.totalCartPrice,
            count = uiState.totalCartCount,
            onDiscountCodeChange = { /* Handle discount code change */ },
            onApplyDiscount = { /* Handle discount application */ },
            onCheckout = { /* Handle checkout */ }
        )
    }
}

@Composable
fun CartItemList(uiState: CartUiState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(uiState.cartProductDtoList) { product ->
            CartItem(
                productDto = product,
                onQuantityChange = { /* Handle quantity change */ },
                onRemoveItem = { /* Handle item removal */ },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    productDto: ProductDto,
    onQuantityChange: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Genel padding
                verticalAlignment = Alignment.CenterVertically // Orta hizalama
            ) {
                // Ürün Resmi
                Image(
                    painter = rememberAsyncImagePainter(
                        model = productDto.imageOne ?: R.drawable.loading_img
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(72.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Ürün Bilgileri: Başlık ve Fiyat
                Column(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween // İçerikleri dikeyde ayırma
                ) {
                    // Ürün Başlığı (Sağ üst)
                    Text(
                        text = productDto.title ?: "Product Title",
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.Start) // Başlık resmin üst hizasında
                            .padding(bottom = 16.dp)
                    )

                    // Ürün Fiyatı (Sağ alt)
                    Text(
                        text = "$${productDto.price}",
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.Start) // Fiyat resmin alt hizasında
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Çöp Kutusu ve Miktar Kontrolü
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    // Çöp Kutusu İkonu (Sağ üst)
                    IconButton(
                        onClick = onRemoveItem,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.End) // İkon sağ üstte
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove Item",
                            tint = Color.Red
                        )
                    }

                    // Miktar Kontrolü (Sağ alt)
                    Box(
                        modifier = Modifier
                            .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp) // Padding artırıldı
                            .align(Alignment.End) // Sağ altta
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = {
                                    if ((productDto.count ?: 0) > 1) onQuantityChange(
                                        (productDto.count ?: 1) - 1
                                    )
                                },
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
                                text = "${productDto.count ?: 1}",
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            IconButton(
                                onClick = { onQuantityChange((productDto.count ?: 1) + 1) },
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

@Composable
fun CartFooter(
    discountCode: String,
    total: Double,
    count: Int,
    onDiscountCodeChange: (String) -> Unit,
    onApplyDiscount: () -> Unit,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = discountCode,
            onValueChange = onDiscountCodeChange,
            label = { Text("Enter Discount Code") },
            trailingIcon = {
                Button(
                    modifier = Modifier.padding(end = 8.dp),
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
                text = "$count",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

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
                text = "$${total}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(text = "Checkout", onClick = { /*TODO*/ })

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    CartScreen(
        uiState = CartUiState(
            cartProductDtoList = listOf(
                ProductDto(
                    title = "Woman Sweater",
                    price = 70.00,
                    count = 1,
                    imageOne = "",
                    salePrice = 0.0,
                    description = "This",
                    category = "Clothing",
                    rate = 4.5,
                    id = 1,
                    imageTwo = "",
                    imageThree = "",
                    saleState = false
                ),
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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
                ProductDto(
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