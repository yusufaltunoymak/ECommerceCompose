package com.hoy.ecommercecompose.ui.resetpassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.LocalFontSizes

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
            .padding(LocalDimensions.current.sixteen),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(LocalDimensions.current.fortyEight)
                .border(
                    BorderStroke(LocalDimensions.current.one, LocalColors.current.primary),
                    shape = RoundedCornerShape(LocalDimensions.current.twelve)
                )
        ) {
            Icon(
                modifier = Modifier.size(LocalDimensions.current.thirtyEight),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(LocalDimensions.current.twelve))

        Text(
            text = stringResource(R.string.create_new_password),
            fontWeight = FontWeight.Bold,
            fontSize = LocalFontSizes.current.sizeTitle,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(LocalDimensions.current.eight))

        Text(
            text = stringResource(R.string.new_password_instruction),
            fontWeight = FontWeight.Thin,
            fontSize = LocalFontSizes.current.body,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(LocalDimensions.current.twentyFour))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(ResetPasswordContract.ResetPasswordUiAction.ChangeEmail(it)) },
            label = stringResource(R.string.new_password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            isError = uiState.showEmailError,
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(LocalDimensions.current.sixteen))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(ResetPasswordContract.ResetPasswordUiAction.ChangePassword(it)) },
            label = stringResource(R.string.confirm_password),
            isPassword = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            isError = uiState.showPasswordError
        )
        Spacer(modifier = Modifier.height(LocalDimensions.current.twentyFour))

        CustomButton(
            text = stringResource(R.string.reset_password),
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
