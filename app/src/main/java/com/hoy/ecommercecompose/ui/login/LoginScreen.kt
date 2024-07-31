package com.hoy.ecommercecompose.ui.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    navController: NavController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                onAction(LoginContract.LoginUiAction.GoogleSignInResult(intent))
            }
        }
    }

    LaunchedEffect(uiState.googleSignInRequest) {
        uiState.googleSignInRequest?.let { intentSender ->
            signInLauncher.launch(
                IntentSenderRequest.Builder(intentSender).build()
            )
        }
    }

    LaunchedEffect(uiState.isSignIn) {
        if (uiState.isSignIn) {
            Log.e("LoginScreen", "${uiState.isSignIn}")
            navController.navigate("home") {
                Log.e("LoginScreen", "Navigate to home")
                popUpTo("login") { inclusive = false }
            }
            uiState.isSignIn = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(48.dp)
                .border(
                    BorderStroke(1.dp, LocalColors.current.primary),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                modifier = Modifier.size(38.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Welcome back! \nGlad to see you, Again!",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(LoginContract.LoginUiAction.ChangeEmail(it)) },
            label = "Enter your e-mail",
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") },
            isError = uiState.showEmailError
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(LoginContract.LoginUiAction.ChangePassword(it)) },
            label = "Enter your password",
            isPassword = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password"
                )
            },
            isError = uiState.showPasswordError
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_forgot_password),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "Forgot Password?",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Login",
            onClick = { onAction(LoginContract.LoginUiAction.SignInClick) })

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    onBackClick
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(
                color = LocalColors.current.primary,
                thickness = 1.dp,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "or login with",
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Divider(
                color = LocalColors.current.primary,
                thickness = 1.dp,
                modifier = Modifier.weight(1f)
            )
        }

        IconButton(
            onClick = { onAction(LoginContract.LoginUiAction.GoogleSignInClick) },
        modifier = Modifier
            .size(48.dp)
            .border(
                BorderStroke(1.dp, LocalColors.current.primary),
                shape = RoundedCornerShape(12.dp)
            )
        ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = null
        )
    }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
fun Preview(){
    val context = LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)
    LoginScreen(
        onBackClick = { },
        uiState = LoginContract.LoginUiState(),
        onAction = { },
        navController = rememberNavController(),
        googleAuthUiClient = GoogleAuthUiClient(
            context = context,
            oneTapClient = oneTapClient
        )
    )
}