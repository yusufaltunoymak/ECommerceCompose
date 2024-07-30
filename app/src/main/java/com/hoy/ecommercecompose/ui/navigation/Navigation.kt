package com.hoy.ecommercecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hoy.ecommercecompose.ui.signup.SignupRoute

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "signup"
    ) {
        composable("signup") {
            SignupRoute(navController)
        }
    }
}