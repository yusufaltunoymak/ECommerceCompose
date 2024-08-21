package com.hoy.ecommercecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.ui.components.bottomnavigation.BottomNavigationBar
import com.hoy.ecommercecompose.ui.components.bottomnavigation.bottomNavItems
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import com.hoy.ecommercecompose.ui.navigation.SetupNavGraph
import com.hoy.ecommercecompose.ui.theme.ECTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val hideBottomNavRoutes =
                    listOf(
                        "welcome",
                        "login",
                        "signup",
                        "search",
                        "product_detail?productId={productId}",
                        "category_screen?category={category}",
                        "payment"
                    )
                val shouldShowBottomNav = currentRoute !in hideBottomNavRoutes
                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomNav) {
                            BottomNavigationBar(
                                navController = navController,
                                items = bottomNavItems,
                                onItemClick = { item ->
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                onFabClick = {
                                    // FAB tıklama işlemi burada tanımlanabilir
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    SetupNavGraph(
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
