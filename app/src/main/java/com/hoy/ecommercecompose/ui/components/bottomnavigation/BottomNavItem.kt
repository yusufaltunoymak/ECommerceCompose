package com.hoy.ecommercecompose.ui.components.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.hoy.ecommercecompose.R

sealed class BottomNavItem(val name: Int, val icon: ImageVector, val route: String) {
    data object Favorite : BottomNavItem(R.string.favorite, Icons.Default.Favorite, "favorite")
    data object Cart : BottomNavItem(R.string.cart, Icons.Default.ShoppingCart, "cart")
    data object Notification :
        BottomNavItem(R.string.notification, Icons.Default.Notifications, "notification")
    data object Profile : BottomNavItem(R.string.profile, Icons.Default.Person, "profile")
}

@Composable
fun getNavItemName(navItem: BottomNavItem): String {
    return stringResource(id = navItem.name)
}
