package com.hoy.ecommercecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.hoy.ecommercecompose.ui.components.BottomNavigationBar
import com.hoy.ecommercecompose.ui.components.bottomNavItems
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import com.hoy.ecommercecompose.ui.navigation.SetupNavGraph
import com.hoy.ecommercecompose.ui.theme.ECommerceComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceComposeTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController, googleAuthUiClient = googleAuthUiClient)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ECommerceComposeTheme {
        val navController = rememberNavController()
        val context = LocalContext.current
        val oneTapClient = Identity.getSignInClient(context)
        val mockGoogleAuthUiClient = GoogleAuthUiClient(
            context = context,
            oneTapClient = oneTapClient
        )
        MainScreen(navController = navController, googleAuthUiClient = mockGoogleAuthUiClient)
    }
}

@Composable
fun MainScreen(navController: NavHostController, googleAuthUiClient: GoogleAuthUiClient) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomNavRoutes = listOf("welcome", "login", "signup")
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

