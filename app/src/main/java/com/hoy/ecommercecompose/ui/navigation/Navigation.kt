package com.hoy.ecommercecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.ui.cart.CartScreen
import com.hoy.ecommercecompose.ui.cart.CartViewModel
import com.hoy.ecommercecompose.ui.category.CategoryScreen
import com.hoy.ecommercecompose.ui.detail.ProductDetailScreen
import com.hoy.ecommercecompose.ui.favorite.FavoriteScreen
import com.hoy.ecommercecompose.ui.home.HomeScreen
import com.hoy.ecommercecompose.ui.home.HomeViewModel
import com.hoy.ecommercecompose.ui.login.LoginScreen
import com.hoy.ecommercecompose.ui.login.LoginViewModel
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import com.hoy.ecommercecompose.ui.onboarding.WelcomeScreen
import com.hoy.ecommercecompose.ui.profile.ProfileScreen
import com.hoy.ecommercecompose.ui.resetpassword.ResetPasswordScreen
import com.hoy.ecommercecompose.ui.resetpassword.ResetPasswordViewModel
import com.hoy.ecommercecompose.ui.search.SearchScreen
import com.hoy.ecommercecompose.ui.sendmail.SendMailScreen
import com.hoy.ecommercecompose.ui.sendmail.SendMailViewModel
import com.hoy.ecommercecompose.ui.signup.SignUpViewModel
import com.hoy.ecommercecompose.ui.signup.SignupScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    modifier: Modifier = Modifier
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) "home" else "welcome",
        modifier = modifier
    ) {
        composable("welcome") {
            WelcomeScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            val viewModel: SignUpViewModel = hiltViewModel()
            val signupState by viewModel.signUpUiState.collectAsStateWithLifecycle()

            SignupScreen(
                uiState = signupState,
                onAction = viewModel::onAction,
                navController = navController,
                onBackClick = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val loginViewState by loginViewModel.loginUiState.collectAsStateWithLifecycle()

            LoginScreen(
                onBackClick = { navController.popBackStack() },
                uiState = loginViewState,
                onAction = loginViewModel::onAction,
                navController = navController,
                googleAuthUiClient = googleAuthUiClient,
                onForgotPasswordClick = { navController.navigate("send_mail") },
                viewModel = loginViewModel
            )
        }
        composable("reset_password") {
            val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            val resetPasswordUiState by resetPasswordViewModel.resetUiState.collectAsStateWithLifecycle()

            ResetPasswordScreen(
                onBackClick = { navController.popBackStack() },
                uiState = resetPasswordUiState,
                onAction = resetPasswordViewModel::onAction,
                navController = navController
            )
        }
        composable("send_mail") {
            val sendMailViewModel: SendMailViewModel = hiltViewModel()
            val sendMailUiState by sendMailViewModel.sendMailUiState.collectAsStateWithLifecycle()

            SendMailScreen(
                onBackClick = { navController.popBackStack() },
                uiState = sendMailUiState,
                onAction = sendMailViewModel::onAction,
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                onNavigateToDetail = {
                    navController.navigate("product_detail?productId=${it}")
                },
                onNavigateToSearch = {
                    navController.navigate("search")
                },
                onCategoryListClick = {
                    navController.navigate("category_screen?category=${it}")
                },
                onAction = homeViewModel::onAction,
                uiState = homeUiState
            )
        }

        composable(
            route = "product_detail?productId={productId}",
            arguments = listOf(navArgument(name = "productId") {
                type = NavType.IntType
                defaultValue = 423334
            })
        ) {
            ProductDetailScreen()
        }

        composable(
            route = "category_screen?category={category}",
            arguments = listOf(navArgument(name = "category") {
                type = NavType.StringType
                defaultValue = "milk"
            })
        ) {
            CategoryScreen(
                onNavigateToDetail = {
                    navController.navigate("product_detail?productId=${it}")
                }
            )
        }

        composable("favorite") {
            FavoriteScreen(navController)
        }
        composable("cart") {
            val cartViewModel: CartViewModel = hiltViewModel()
            val cartUiState by cartViewModel.uiState.collectAsStateWithLifecycle()

            CartScreen(
                navController = navController,
                uiState = cartUiState
            )
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("search") {

            SearchScreen(
                navController = navController,
                onClick = {
                    navController.navigate("product_detail?productId=${it}")
                }
            )
        }
    }
}