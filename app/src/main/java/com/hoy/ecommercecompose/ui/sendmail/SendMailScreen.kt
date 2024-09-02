package com.hoy.ecommercecompose.ui.sendmail

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.collectWithLifecycle
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SendMailScreen(
    onBackClick: () -> Unit,
    uiState: SendMailContract.SendMailUiState,
    onAction: (SendMailContract.SendMailUiAction) -> Unit,
    onNavigateToLogin: () -> Unit,
    uiEffect: Flow<SendMailContract.UiEffect>
) {
    val isEmailFieldEmpty = uiState.email.isEmpty()
    val context = LocalContext.current

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is SendMailContract.UiEffect.ShowToast -> {
                Toast.makeText(context, context.getString(effect.messageResId), Toast.LENGTH_LONG).show() }

            SendMailContract.UiEffect.NavigateToLogin -> { onNavigateToLogin() }
            SendMailContract.UiEffect.BackClick -> { onBackClick() }
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
                modifier = Modifier.size(ECTheme.dimensions.thirtySix),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twelve))

        Text(
            text = stringResource(id = R.string.send_mail_screen_title),
            fontWeight = FontWeight.Bold,
            fontSize = ECTheme.typography.sizeTitle,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

        Text(
            text = stringResource(id = R.string.send_mail_screen_description),
            fontWeight = FontWeight.Thin,
            fontSize = ECTheme.typography.medium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(SendMailContract.SendMailUiAction.SendEmailAction(it)) },
            label = stringResource(id = R.string.send_mail_email_label),
            isPassword = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null
                )
            },
            isError = uiState.showEmailError
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomButton(
            text = stringResource(id = R.string.send_mail_button_text),
            onClick = {
                onAction(
                    SendMailContract.SendMailUiAction.SendMail,
                )
            },
            enabled = !isEmailFieldEmpty
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.click_to_login_text),
            fontWeight = FontWeight.Bold,
            fontSize = ECTheme.typography.medium,
            color = ECTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onNavigateToLogin()
                },
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SendMailScreenPreview() {
    SendMailScreen(
        onBackClick = {},
        uiState = SendMailContract.SendMailUiState(),
        onAction = {},
        onNavigateToLogin = {},
        uiEffect = flowOf()
    )
}
