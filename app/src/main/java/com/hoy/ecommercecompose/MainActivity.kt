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
import com.hoy.ecommercecompose.ui.navigation.NavRoute
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
                val hideBottomNavRoutes = listOf(
                    NavRoute.WELCOME.route,
                    NavRoute.LOGIN.route,
                    NavRoute.SIGNUP.route,
                    NavRoute.SEARCH.route,
                    NavRoute.PRODUCT_DETAIL.route,
                    NavRoute.CATEGORY_SCREEN.route,
                    NavRoute.PAYMENT.route
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
                                    navController.navigate(NavRoute.ORDER.route) {
                                        launchSingleTop = true
                                        
                                    }
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
