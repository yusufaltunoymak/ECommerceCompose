package com.hoy.ecommercecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.ui.account.AccountScreen
import com.hoy.ecommercecompose.ui.account.AccountViewModel
import com.hoy.ecommercecompose.ui.cart.CartScreen
import com.hoy.ecommercecompose.ui.cart.CartViewModel
import com.hoy.ecommercecompose.ui.category.CategoryScreen
import com.hoy.ecommercecompose.ui.category.CategoryViewModel
import com.hoy.ecommercecompose.ui.changepassword.ChangePasswordScreen
import com.hoy.ecommercecompose.ui.changepassword.ChangePasswordViewModel
import com.hoy.ecommercecompose.ui.detail.ProductDetailScreen
import com.hoy.ecommercecompose.ui.detail.ProductDetailViewModel
import com.hoy.ecommercecompose.ui.favorite.FavoriteScreen
import com.hoy.ecommercecompose.ui.favorite.FavoriteViewModel
import com.hoy.ecommercecompose.ui.home.HomeScreen
import com.hoy.ecommercecompose.ui.home.HomeViewModel
import com.hoy.ecommercecompose.ui.login.LoginScreen
import com.hoy.ecommercecompose.ui.login.LoginViewModel
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import com.hoy.ecommercecompose.ui.notification.NotificationScreen
import com.hoy.ecommercecompose.ui.notification.NotificationViewModel
import com.hoy.ecommercecompose.ui.onboarding.WelcomeScreen
import com.hoy.ecommercecompose.ui.order.OrderScreen
import com.hoy.ecommercecompose.ui.order.OrderViewModel
import com.hoy.ecommercecompose.ui.payment.PaymentScreen
import com.hoy.ecommercecompose.ui.payment.PaymentViewModel
import com.hoy.ecommercecompose.ui.search.SearchScreen
import com.hoy.ecommercecompose.ui.search.SearchViewModel
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
        startDestination = if (currentUser != null) NavRoute.HOME.route else NavRoute.WELCOME.route,
        modifier = modifier
    ) {
        composable(NavRoute.WELCOME.route) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(NavRoute.LOGIN.route) },
                onRegisterClick = { navController.navigate(NavRoute.SIGNUP.route) }
            )
        }
        composable(NavRoute.SIGNUP.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val signupState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect

            SignupScreen(
                uiState = signupState,
                onAction = viewModel::onAction,
                uiEffect = uiEffect,
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(NavRoute.HOME.route) {
                        popUpTo(NavRoute.WELCOME.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoute.LOGIN.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val loginViewState by loginViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = loginViewModel.uiEffect

            LoginScreen(
                uiState = loginViewState,
                uiEffect = uiEffect,
                onAction = loginViewModel::onAction,
                onForgotPasswordClick = { navController.navigate(NavRoute.SEND_MAIL.route) },
                onNavigateToHome = {
                    navController.navigate(NavRoute.HOME.route) {
                        popUpTo(NavRoute.WELCOME.route) { inclusive = true }
                    }
                },
                onBackClick = { navController.navigateUp() },
            )
        }
        composable(NavRoute.RESET_PASSWORD.route) {
            val changePasswordViewModel: ChangePasswordViewModel = hiltViewModel()
            val changePasswordUiState by changePasswordViewModel.uiState.collectAsStateWithLifecycle()
            val changePasswordUiEffect = changePasswordViewModel.uiEffect

            ChangePasswordScreen(
                onBackClick = { navController.popBackStack() },
                uiState = changePasswordUiState,
                onAction = changePasswordViewModel::onAction,
                uiEffect = changePasswordUiEffect
            )
        }
        composable(NavRoute.SEND_MAIL.route) {
            val sendMailViewModel: SendMailViewModel = hiltViewModel()
            val sendMailUiState by sendMailViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = sendMailViewModel.uiEffect

            SendMailScreen(
                onBackClick = { navController.popBackStack() },
                uiState = sendMailUiState,
                onAction = sendMailViewModel::onAction,
                uiEffect = uiEffect,
                onNavigateToLogin = { navController.navigate(NavRoute.LOGIN.route) }
            )
        }

        composable(NavRoute.HOME.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = homeViewModel.uiEffect
            LaunchedEffect(Unit) {
                homeViewModel.getProducts()
                homeViewModel.getSpecialProducts()
            }

            HomeScreen(
                onNavigateToDetail = { productId ->
                    navController.navigate(NavRoute.PRODUCT_DETAIL.withArgs("productId=$productId"))
                },
                onNavigateToSearch = {
                    navController.navigate(NavRoute.SEARCH.route)
                },
                onCategoryListClick = { category ->
                    navController.navigate(NavRoute.CATEGORY_SCREEN.withArgs("category=$category"))
                },
                onAction = homeViewModel::onAction,
                uiState = homeUiState,
                uiEffect = uiEffect
            )
        }

        composable(
            route = NavRoute.PRODUCT_DETAIL.route,
            arguments = listOf(
                navArgument(name = "productId") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: ProductDetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProductDetailScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onBackClick = { navController.popBackStack() },
                context = LocalContext.current
            )
        }

        composable(
            route = NavRoute.CATEGORY_SCREEN.route,
            arguments = listOf(
                navArgument(name = "category") {
                    type = NavType.StringType
                    defaultValue = "milk"
                }
            )
        ) {
            val viewModel: CategoryViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            LaunchedEffect(uiState.searchQuery) {
                if (uiState.searchQuery.isEmpty()) {
                    val category = viewModel.getCategory()
                    viewModel.getProductsByCategory(category)
                } else {
                    viewModel.searchProducts(query = uiState.searchQuery)
                }
            }
            CategoryScreen(
                uiEffect = uiEffect,
                uiState = uiState,
                onAction = viewModel::onAction,
                onBackClick = { navController.popBackStack() },
                onNavigateToDetail = {
                    navController.navigate(NavRoute.PRODUCT_DETAIL.withArgs("productId=$it"))
                }
            )
        }

        composable(NavRoute.FAVORITE.route) {
            val viewModel: FavoriteViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            LaunchedEffect(Unit) {
                viewModel.loadFavorites()
            }
            FavoriteScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onNavigateToDetail = {
                    navController.navigate(NavRoute.PRODUCT_DETAIL.withArgs("productId=$it"))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(NavRoute.PROFILE.route) {
            val viewModel: AccountViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            AccountScreen(
                uiEffect = uiEffect,
                uiState = uiState,
                onAction = viewModel::onAction,
                onNavigateToPassword = { navController.navigate(NavRoute.RESET_PASSWORD.route) },
                onNavigateToWelcome = {
                    navController.navigate(NavRoute.WELCOME.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToNotifications = { navController.navigate(NavRoute.NOTIFICATION.route) },
            )
        }
        composable(NavRoute.SEARCH.route) {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val searchUiState by searchViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = searchViewModel.uiEffect
            LaunchedEffect(Unit) {
                searchViewModel.loadAllProducts()
            }
            SearchScreen(
                onDetailClick = {
                    navController.navigate(NavRoute.PRODUCT_DETAIL.withArgs("productId=$it"))
                },
                onAction = searchViewModel::onAction,
                uiState = searchUiState,
                uiEffect = uiEffect,
                onBackClick = { navController.popBackStack() }

            )
        }
        composable(NavRoute.CART.route) {
            val cartViewModel: CartViewModel = hiltViewModel()
            val cartUiState by cartViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = cartViewModel.uiEffect
            LaunchedEffect(Unit) {
                cartViewModel.getCartProducts()
            }
            CartScreen(
                uiState = cartUiState,
                onAction = cartViewModel::onAction,
                uiEffect = uiEffect,
                onNavigatePayment = { navController.navigate(NavRoute.PAYMENT.route) }
            )
        }
        composable(NavRoute.PAYMENT.route) {
            val paymentViewModel: PaymentViewModel = hiltViewModel()
            val paymentUiState by paymentViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = paymentViewModel.uiEffect
            PaymentScreen(
                uiState = paymentUiState,
                onAction = paymentViewModel::onAction,
                uiEffect = uiEffect,
                onBackPress = { navController.popBackStack() }
            )
        }
        composable(NavRoute.ORDER.route) {
            val orderViewModel: OrderViewModel = hiltViewModel()
            val orderUiState by orderViewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = orderViewModel.uiEffect
            LaunchedEffect(Unit) {
                orderViewModel.loadOrders()
            }
            OrderScreen(
                uiState = orderUiState,
                onAction = orderViewModel::onAction,
                uiEffect = uiEffect,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(NavRoute.NOTIFICATION.route) {
            val viewModel: NotificationViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            NotificationScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
                uiEffect = uiEffect,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
