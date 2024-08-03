package com.hoy.ecommercecompose.ui.sendmail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Send
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
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun SendMailScreen(
    onBackClick: () -> Unit,
    uiState: SendMailContract.SendMailUiState,
    onAction: (SendMailContract.SendMailUiAction) -> Unit,
    navController: NavController
){
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
            text = "Ups! \nForgot password?",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Don't worry! It occurs. \nPlease enter the email address linked with your account.",
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(SendMailContract.SendMailUiAction.SendEmailAction(it)) },
            label = "Enter your email",
            isPassword = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription =null
                )
            },
            isError = uiState.showEmailError
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Send Mail",
            onClick = { onAction(SendMailContract.SendMailUiAction.SendMail) })

        Spacer(modifier = Modifier.height(400.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Click to login",
                fontWeight = FontWeight.Thin,
                fontSize = 18.sp,
                color = LocalColors.current.primary,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prew() {
}