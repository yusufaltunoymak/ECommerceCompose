package com.hoy.ecommercecompose.ui.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.ui.components.CustomAlertDialog
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun LoginScreen(
    uiState: LoginContract.UiState,
    uiEffect: Flow<LoginContract.UiEffect>,
    onAction: (LoginContract.UiAction) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,

) {
    var alertDialogState by remember { mutableStateOf(false) }

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                onAction(LoginContract.UiAction.GoogleSignInResult(intent))
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

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is LoginContract.UiEffect.ShowAlertDialog -> alertDialogState = true
            is LoginContract.UiEffect.GoToHome -> onNavigateToHome()
            is LoginContract.UiEffect.GoToForgotPasswordClick -> onForgotPasswordClick()
            is LoginContract.UiEffect.GoToWelcomeScreen -> {
                onBackClick()
            }
        }
    }

    if (alertDialogState) {
        CustomAlertDialog(
            errorMessage = uiState.signInError,
            onDismiss = {
                onAction(LoginContract.UiAction.ClearError)
                alertDialogState = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ECTheme.dimensions.sixteen),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(ECTheme.dimensions.fortyEight)
                    .border(
                        BorderStroke(ECTheme.dimensions.one, ECTheme.colors.primary),
                        shape = RoundedCornerShape(ECTheme.dimensions.twelve)
                    )
            ) {
                Icon(
                    modifier = Modifier.size(ECTheme.dimensions.thirtySix),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(ECTheme.dimensions.twelve))

            Text(
                text = stringResource(id = R.string.welcome_login_text),
                fontWeight = FontWeight.Bold,
                fontSize = ECTheme.typography.sizeTitle,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

            CustomTextField(
                value = uiState.email,
                onValueChange = { onAction(LoginContract.UiAction.ChangeEmail(it)) },
                label = stringResource(id = R.string.enter_mail_text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(id = R.string.email_text)
                    )
                }
            )

            Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

            CustomTextField(
                value = uiState.password,
                onValueChange = { onAction(LoginContract.UiAction.ChangePassword(it)) },
                label = stringResource(id = R.string.enter_password_text),
                isPassword = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(id = R.string.password_text)
                    )
                }
            )

            Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_forgot_password),
                    contentDescription = null,
                    modifier = Modifier.size(ECTheme.dimensions.twenty)
                )
                Text(
                    modifier = Modifier.clickable { onForgotPasswordClick() },
                    text = stringResource(id = R.string.forgot_password_text),
                    fontWeight = FontWeight.Light,
                    fontSize = ECTheme.typography.small,

                )
            }

            Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

            CustomButton(
                text = stringResource(id = R.string.login_text),
                onClick = { onAction(LoginContract.UiAction.SignInClick) },
            )

            Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ECTheme.dimensions.sixteen),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    color = ECTheme.colors.primary,
                    thickness = ECTheme.dimensions.one,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(ECTheme.dimensions.eight))

                Text(
                    text = stringResource(id = R.string.or_login_with_text),
                    fontSize = ECTheme.typography.medium,
                    color = ECTheme.colors.black
                )

                Spacer(modifier = Modifier.width(ECTheme.dimensions.eight))

                Divider(
                    color = ECTheme.colors.primary,
                    thickness = ECTheme.dimensions.one,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ECTheme.dimensions.sixteen),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { onAction(LoginContract.UiAction.GoogleSignInClick) },
                    modifier = Modifier
                        .size(ECTheme.dimensions.fortyEight)
                        .border(
                            BorderStroke(ECTheme.dimensions.one, ECTheme.colors.primary),
                            shape = RoundedCornerShape(ECTheme.dimensions.twelve)
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = null
                    )
                }
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                color = ECTheme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginContract.UiState(),
        uiEffect = flowOf(),
        onAction = {},
        onForgotPasswordClick = {},
        onBackClick = {},
        onNavigateToHome = {}
    )
}
