package com.hoy.ecommercecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hoy.ecommercecompose.ui.home.HomeScreen
import com.hoy.ecommercecompose.ui.login.LoginScreen
import com.hoy.ecommercecompose.ui.login.LoginViewModel
import com.hoy.ecommercecompose.ui.onboarding.WelcomeScreen
import com.hoy.ecommercecompose.ui.signup.SignUpViewModel
import com.hoy.ecommercecompose.ui.signup.SignupScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {

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
            //collectAsStateWithLifecycle daha sağlıklıymış
            val signupState by viewModel.signUpUiState.collectAsState()

            SignupScreen(
                uiState = signupState,
                onAction = viewModel::onAction,
                navController = navController
            )
        }

        composable("login") {
            val loginViewModel : LoginViewModel = hiltViewModel()
            val loginViewState by loginViewModel.loginUiState.collectAsState()

            LoginScreen(
                onBackClick = { navController.popBackStack() },
                uiState = loginViewState,
                onAction = loginViewModel::onAction,
                navController = navController
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}