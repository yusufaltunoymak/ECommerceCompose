package com.hoy.ecommercecompose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.ui.ConnectivityViewModel
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
    private lateinit var navController: NavHostController
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setContent {
            MainContent(intent)
        }
    }

    @Composable
    fun MainContent(intent: Intent) {
        ECTheme {
            navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val hideBottomNavRoutes = listOf(
                NavRoute.WELCOME.route,
                NavRoute.LOGIN.route,
                NavRoute.SIGNUP.route,
                NavRoute.SEARCH.route,
                NavRoute.PRODUCT_DETAIL.route,
                NavRoute.CATEGORY_SCREEN.route,
                NavRoute.PAYMENT.route,
                NavRoute.SEND_MAIL.route,
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
                                        Log.e("MainActivity", "popUpTo")
                                        saveState = true
                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
                                }
                            },
                            onFabClick = {
                                navController.navigate(NavRoute.CART.route)
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

            LaunchedEffect(intent) {
                handleDeepLink(intent)
            }

            ConnectivityStatus()
        }
    }

    @Composable
    fun ConnectivityStatus() {
        connectivityViewModel.isConnected.collectAsState().value.let { isConnected ->
            if (!isConnected) {
                showNoInternetDialog()
            }
        }
    }

    private fun handleDeepLink(intent: Intent) {
        val data: Uri? = intent.data
        data?.let {
            val productId = it.lastPathSegment?.toIntOrNull()
            productId?.let { id ->
                navController.navigate(NavRoute.PRODUCT_DETAIL.withArgs("productId=$id"))
            }
        }
    }

    private fun showNoInternetDialog() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }
}
