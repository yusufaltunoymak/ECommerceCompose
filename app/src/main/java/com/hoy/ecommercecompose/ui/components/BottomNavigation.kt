package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.ui.theme.LocalColors

sealed class BottomNavItem(val name: String, val icon: ImageVector, val route: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Favorite : BottomNavItem("favorite", Icons.Default.Favorite, "favorite")
    data object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "cart")
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "profile")
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    onFabClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp)),
            elevation = 8.dp
        ) {
            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = Color.White
            ) {
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.name,
                                tint = LocalColors.current.primary
                            )
                        },
                        label = null,
                        selected = currentRoute == item.route,
                        onClick = { onItemClick(item) },
                        alwaysShowLabel = false // Ensures only icons are shown
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onFabClick,
            backgroundColor = LocalColors.current.primary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -28.dp) // Adjust the FAB position
        ) {
            Icon(Icons.Default.Home, contentDescription = "Home") // Center FAB Icon
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Favorite,
    BottomNavItem.Cart,
    BottomNavItem.Profile
)

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()

    BottomNavigationBar(
        navController = navController,
        items = bottomNavItems,
        onItemClick = { /* Preview Click Handling */ },
        onFabClick = { /* FAB Click Handling */ }
    )
}
