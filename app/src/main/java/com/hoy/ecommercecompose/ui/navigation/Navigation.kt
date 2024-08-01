package com.hoy.ecommercecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hoy.ecommercecompose.ui.home.HomeViewModel
import com.hoy.ecommercecompose.ui.home.HomeScreen
import com.hoy.ecommercecompose.ui.login.LoginScreen
import com.hoy.ecommercecompose.ui.login.LoginViewModel
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import com.hoy.ecommercecompose.ui.onboarding.WelcomeScreen
import com.hoy.ecommercecompose.ui.signup.SignUpViewModel
import com.hoy.ecommercecompose.ui.signup.SignupScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            val viewModel : SignUpViewModel = hiltViewModel()
            val signupState by viewModel.signUpUiState.collectAsStateWithLifecycle()

            SignupScreen(
                uiState = signupState,
                onAction = viewModel::onAction,
                navController = navController,
                onBackClick = { navController.popBackStack() },
            )
        }

        composable("login") {
            val loginViewModel : LoginViewModel = hiltViewModel()
            val loginViewState by loginViewModel.loginUiState.collectAsStateWithLifecycle()

            LoginScreen(
                onBackClick = { navController.popBackStack() },
                uiState = loginViewState,
                onAction = loginViewModel::onAction,
                navController = navController,
                googleAuthUiClient = googleAuthUiClient
            )
        }


        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(uiState = uiState)
        }
    }
}