package com.hoy.ecommercecompose.ui.resetpassword

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
    uiState: ResetPasswordContract.ResetPasswordUiState,
    onAction: (ResetPasswordContract.ResetPasswordUiAction) -> Unit,
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
            text = "Create new password",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your new password must be unique from those previously used.",
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(ResetPasswordContract.ResetPasswordUiAction.ChangeEmail(it)) },
            label = "New Password",
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
            isError = uiState.showEmailError,
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(ResetPasswordContract.ResetPasswordUiAction.ChangePassword(it)) },
            label = "Confirm Password",
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
            text = "Reset Password",
            onClick = { onAction(ResetPasswordContract.ResetPasswordUiAction.ResetPassword) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen(
        onBackClick = { },
        uiState = ResetPasswordContract.ResetPasswordUiState(),
        onAction = { },
        navController = rememberNavController()
    )
}
