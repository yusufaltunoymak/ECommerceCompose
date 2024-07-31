package com.hoy.ecommercecompose.ui.signup

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun SignupScreen(
    onBackClick: () -> Unit,
    uiState: SignUpContract.UiState,
    onAction : (SignUpContract.UiAction) -> Unit,
    navController: NavController
) {
    LaunchedEffect(uiState.isSignUp) {
        if (uiState.isSignUp) {
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
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
            text = "Hello! Register to get started!",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        CustomTextField(
            value = uiState.name,
            onValueChange = { onAction(SignUpContract.UiAction.ChangeName(it)) },
            label = "Name",
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
        )
        CustomTextField(
            value = uiState.surname,
            onValueChange = { onAction(SignUpContract.UiAction.ChangeSurname(it)) },
            label = "Surname",
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
        )
        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(SignUpContract.UiAction.ChangeEmail(it)) },
            label = "Email",
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
        )
        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(SignUpContract.UiAction.ChangePassword(it)) },
            label = "Password",
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(24.dp))


        CustomButton(
            text = "Register",
            onClick = {
                onAction(SignUpContract.UiAction.SignUpClick)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    SignupScreen(
        uiState = SignUpContract.UiState(),
        onAction = { },
        navController = rememberNavController(),
        onBackClick = {  },)
}
