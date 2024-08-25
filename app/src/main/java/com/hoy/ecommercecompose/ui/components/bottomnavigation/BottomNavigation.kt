package com.hoy.ecommercecompose.ui.components.bottomnavigation

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.theme.ECTheme

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
            shape = RoundedCornerShape(ECTheme.dimensions.sixteen),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(ECTheme.dimensions.fiftySix)
                .clip(RoundedCornerShape(ECTheme.dimensions.sixteen)),
            elevation = ECTheme.dimensions.eight
        ) {
            BottomNavigation(
                backgroundColor = ECTheme.colors.white,
                contentColor = ECTheme.colors.white
            ) {
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = getNavItemName(navItem = item),
                                tint = ECTheme.colors.primary
                            )
                        },
                        selected = currentRoute == item.route,
                        onClick = { onItemClick(item) },
                        alwaysShowLabel = false
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onFabClick,
            backgroundColor = ECTheme.colors.primary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = ECTheme.dimensions.negativeTwentyEight)
        ) {
            Icon(Icons.Default.Home, contentDescription = stringResource(id = R.string.home))
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem.Favorite,
    BottomNavItem.Cart,
    BottomNavItem.Order,
    BottomNavItem.Profile
)

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()

    BottomNavigationBar(
        navController = navController,
        items = bottomNavItems,
        onItemClick = {},
        onFabClick = {}
    )
}
