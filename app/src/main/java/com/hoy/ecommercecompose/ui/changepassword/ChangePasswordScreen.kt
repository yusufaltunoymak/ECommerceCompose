package com.hoy.ecommercecompose.ui.changepassword

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    uiState: ChangePasswordContract.UiState,
    onAction: (ChangePasswordContract.UiAction) -> Unit,
    uiEffect: Flow<ChangePasswordContract.UiEffect>
) {
    val context = LocalContext.current

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is ChangePasswordContract.UiEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            value = uiState.currentPassword,
            onValueChange = { onAction(ChangePasswordContract.UiAction.ChangeCurrentPassword(it)) },
            label = stringResource(R.string.current_password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(ChangePasswordContract.UiAction.ChangePassword(it)) },
            label = stringResource(R.string.new_password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.sixteen))

        CustomTextField(
            value = uiState.confirmPassword,
            onValueChange = { onAction(ChangePasswordContract.UiAction.ChangeConfirmPassword(it)) },
            label = stringResource(R.string.confirm_password),
            isPassword = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
        )
        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomButton(
            text = stringResource(R.string.reset_password),
            onClick = {
                onAction(ChangePasswordContract.UiAction.ResetPassword)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(
        onBackClick = {},
        uiState = ChangePasswordContract.UiState(),
        onAction = {},
        uiEffect = flowOf()
    )
}
