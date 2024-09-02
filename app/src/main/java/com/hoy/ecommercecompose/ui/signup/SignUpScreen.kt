package com.hoy.ecommercecompose.ui.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.ui.components.CustomAlertDialog
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun SignupScreen(
    uiState: SignUpContract.UiState,
    uiEffect: Flow<SignUpContract.UiEffect>,
    onAction: (SignUpContract.UiAction) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    var alertDialogState by remember { mutableStateOf(false) }

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is SignUpContract.UiEffect.ShowAlertDialog -> alertDialogState = true
            is SignUpContract.UiEffect.GoToMainScreen -> onNavigateToHome()
        }
    }

    if (alertDialogState) {
        CustomAlertDialog(
            errorMessage = uiState.signUpError,
            onDismiss = {
                onAction(SignUpContract.UiAction.ClearError)
                alertDialogState = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ECTheme.dimensions.eight),
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
                text = stringResource(id = R.string.hello_register_text),
                fontWeight = FontWeight.Bold,
                fontSize = ECTheme.typography.sizeTitle,
                modifier = Modifier.align(Alignment.Start)
            )

            CustomTextField(
                value = uiState.name,
                onValueChange = { onAction(SignUpContract.UiAction.ChangeName(it)) },
                label = stringResource(id = R.string.name_text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )
            CustomTextField(
                value = uiState.surname,
                onValueChange = { onAction(SignUpContract.UiAction.ChangeSurname(it)) },
                label = stringResource(id = R.string.surname_text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )
            CustomTextField(
                value = uiState.email,
                onValueChange = { onAction(SignUpContract.UiAction.ChangeEmail(it)) },
                label = stringResource(id = R.string.email_text),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
            )
            CustomTextField(
                value = uiState.password,
                isPassword = true,
                onValueChange = { onAction(SignUpContract.UiAction.ChangePassword(it)) },
                label = stringResource(id = R.string.password_text),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

            CustomButton(
                text = stringResource(id = R.string.register_text),
                onClick = { onAction(SignUpContract.UiAction.SignUpClick) },
            )
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
