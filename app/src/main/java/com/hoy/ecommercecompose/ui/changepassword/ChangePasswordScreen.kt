package com.hoy.ecommercecompose.ui.changepassword

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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.ECTheme

@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    uiState: ChangePasswordContract.UiState,
    onAction: (ChangePasswordContract.UiAction) -> Unit,
) {
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
                modifier = Modifier.size(ECTheme.dimensions.thirtyEight),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twelve))

        Text(
            text = stringResource(R.string.create_new_password),
            fontWeight = FontWeight.Bold,
            fontSize = ECTheme.typography.sizeTitle,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

        Text(
            text = stringResource(R.string.new_password_instruction),
            fontWeight = FontWeight.Thin,
            fontSize = ECTheme.typography.body,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomTextField(
            value = uiState.email,
            onValueChange = { },
            label = stringResource(R.string.new_password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            isError = uiState.showEmailError,
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(ChangePasswordContract.UiAction.ChangePassword(it)) },
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
        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomButton(
            text = stringResource(R.string.reset_password),
            onClick = { onAction(ChangePasswordContract.UiAction.ResetPassword) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    ChangePasswordScreen(
        onBackClick = { },
        uiState = ChangePasswordContract.UiState(),
        onAction = { },
    )
}
