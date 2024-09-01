package com.hoy.ecommercecompose.ui.components.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.hoy.ecommercecompose.R

sealed class BottomNavItem(
    val name: Int,
    val icon: ImageVector? = null,
    val route: String,
    @DrawableRes val drawableIcon: Int? = null
) {
    data object Favorite : BottomNavItem(R.string.favorite, Icons.Default.Favorite, "favorite")
    data object Cart : BottomNavItem(R.string.cart, Icons.Default.ShoppingCart, "cart")
    data object Order : BottomNavItem(R.string.order, drawableIcon = R.drawable.ic_order, route = "order")
    data object Profile : BottomNavItem(R.string.profile, Icons.Default.Person, "profile")
}

@Composable
fun getNavItemName(navItem: BottomNavItem): String {
    return stringResource(id = navItem.name)
}
