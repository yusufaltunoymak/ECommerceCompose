package com.hoy.ecommercecompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton


@Composable
fun PasswordChangedScreen(
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.checked), contentDescription = null, modifier = Modifier.size(300.dp))

        Text(
            text = "Password Changed!",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row (

        ){
            Text(
                text = "Your password has been changed successfully.",
                fontWeight = FontWeight.Thin,
                fontSize = 18.sp,
                //modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Back to Login",
            onClick = { })
    }
}


@Preview(showBackground = true)
@Composable
fun PasswordChangedScreenPrew() {
    PasswordChangedScreen(
        uiState = LoginContract.LoginUiState(),
        onAction = { },
        navController = rememberNavController()
    )
}