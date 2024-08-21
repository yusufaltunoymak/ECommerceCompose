package com.hoy.ecommercecompose.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomOutlinedButton
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.LocalFontSizes

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimensions.current.sixteen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = stringResource(id = R.string.desc_welcome),
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.app_name_hoy),
            fontSize = LocalFontSizes.current.sizeTitle,
            fontWeight = FontWeight.Thin,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = stringResource(id = R.string.ecommerce_title),
            fontSize = LocalFontSizes.current.sizeTitle,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(LocalDimensions.current.fortyEight))

        CustomButton(text = stringResource(id = R.string.login_but), onClick = onLoginClick)
        CustomOutlinedButton(
            text = stringResource(id = R.string.register_but),
            onClick = onRegisterClick
        )
        Spacer(modifier = Modifier.fillMaxHeight())
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onLoginClick = {},
        onRegisterClick = {}
    )
}
