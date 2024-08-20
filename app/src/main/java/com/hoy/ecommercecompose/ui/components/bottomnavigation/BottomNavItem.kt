package com.hoy.ecommercecompose.ui.components.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val name: String, val icon: ImageVector, val route: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Favorite : BottomNavItem("favorite", Icons.Default.Favorite, "favorite")
    data object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "cart")
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "profile")
}
