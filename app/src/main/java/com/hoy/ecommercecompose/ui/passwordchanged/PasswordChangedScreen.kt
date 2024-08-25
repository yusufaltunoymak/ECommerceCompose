package com.hoy.ecommercecompose.ui.passwordchanged

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.theme.ECTheme

@Composable
fun PasswordChangedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ECTheme.dimensions.sixteen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.checked),
            contentDescription = null,
            modifier = Modifier.size(ECTheme.dimensions.threeHundred)
        )

        Text(
            text = stringResource(R.string.password_changed),
            fontWeight = FontWeight.Bold,
            fontSize = ECTheme.typography.sizeTitle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(ECTheme.dimensions.eight))

        Text(
            text = stringResource(R.string.password_changed_message),
            fontWeight = FontWeight.Thin,
            fontSize = ECTheme.typography.body,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(ECTheme.dimensions.twentyFour))

        CustomButton(
            text = stringResource(R.string.back_to_login),
            onClick = { /* Handle back to login */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordChangedScreenPreview() {
    PasswordChangedScreen()
}
