package com.hoy.ecommercecompose.ui.passwordchanged

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.LocalFontSizes
import com.hoy.ecommercecompose.ui.login.LoginContract

@Composable
fun PasswordChangedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimensions.current.sixteen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.checked),
            contentDescription = null,
            modifier = Modifier.size(LocalDimensions.current.threeHundred)
        )

        Text(
            text = stringResource(R.string.password_changed),
            fontWeight = FontWeight.Bold,
            fontSize = LocalFontSizes.current.sizeTitle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(LocalDimensions.current.eight))

        Text(
            text = stringResource(R.string.password_changed_message),
            fontWeight = FontWeight.Thin,
            fontSize = LocalFontSizes.current.body,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(LocalDimensions.current.twentyFour))

        CustomButton(
            text = stringResource(R.string.back_to_login),
            onClick = { /* Handle back to login */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordChangedScreenPreview() {
    PasswordChangedScreen(
    )
}
