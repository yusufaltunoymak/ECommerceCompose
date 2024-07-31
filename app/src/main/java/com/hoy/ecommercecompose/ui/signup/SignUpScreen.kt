package com.hoy.ecommercecompose.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField


/**
 * SignupRoute
 * SignupViewModel'i kullanarak kayıt işlemlerini gerçekleştirir
 * ve gerekli kullanıcı arayüzünü sunar. Kullanıcı kayıt olduktan sonra
 * başarı durumuna göre başka bir sayfaya yönlendirme işlemini gerçekleştiri
 */

@Composable
fun SignupScreen(
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(24.dp))

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
        navController = rememberNavController())
}
