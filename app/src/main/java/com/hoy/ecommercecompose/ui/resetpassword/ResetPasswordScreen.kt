package com.hoy.ecommercecompose.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun ResetPasswordScreen(
    onBackClick: () -> Unit,
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    navController: NavController
) {

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
            text = "Ups! \nForgot password",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(LoginContract.LoginUiAction.ChangeEmail(it)) },
            label = "Enter new password",
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
            isError = uiState.showEmailError
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(LoginContract.LoginUiAction.ChangePassword(it)) },
            label = "Enter new password again",
            isPassword = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password"
                )
            },
            isError = uiState.showPasswordError
        )
        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Change Password",
            onClick = { })
    }
}


@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPrew() {
    ResetPasswordScreen(
        onBackClick = { },
        uiState = LoginContract.LoginUiState(),
        onAction = { },
        navController = rememberNavController()
    )
}