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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomOutlinedButton

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "welcome image",
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
        )
        Text(text = "hoy", fontSize = 32.sp, fontWeight = FontWeight.Thin, fontStyle = FontStyle.Italic)
        Text(text = "E-Commerce", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(48.dp))

        CustomButton(text = "Login", onClick = onLoginClick)
        CustomOutlinedButton(text = "Register", onClick = onRegisterClick)

        Spacer(modifier = Modifier.fillMaxHeight())
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onLoginClick = { /*TODO*/ }) {
    }
}
