package com.hoy.ecommercecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hoy.ecommercecompose.ui.signup.SignUpViewModel
import com.hoy.ecommercecompose.ui.signup.SignupScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "signup"
    ) {
        composable("signup") {
            val viewModel : SignUpViewModel = hiltViewModel()
            //collectAsStateWithLifecycle daha sağlıklıymış
            val signupState by viewModel.signUpUiState.collectAsState()

            SignupScreen(
                uiState = signupState,
                onAction = viewModel::onAction
            )
        }
    }
}