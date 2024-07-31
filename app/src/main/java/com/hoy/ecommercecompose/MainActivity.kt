package com.hoy.ecommercecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
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
                SetupNavGraph(navController = navController, googleAuthUiClient = googleAuthUiClient)
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
        SetupNavGraph(navController = navController, googleAuthUiClient = mockGoogleAuthUiClient)
    }
}

