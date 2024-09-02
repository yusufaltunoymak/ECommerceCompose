package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.ui.theme.ECTheme

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    leadingIcon: @Composable () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val containerColor =
        if (isError) ECTheme.colors.customButtonColor else ECTheme.colors.white
    val indicatorColor =
        if (isError) ECTheme.colors.red else ECTheme.colors.primary.copy(alpha = 0.3f)

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = { leadingIcon() },
            label = { Text(label) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(ECTheme.dimensions.eight),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedIndicatorColor = indicatorColor,
                unfocusedIndicatorColor = indicatorColor,
            ),
            isError = isError,
            singleLine = true
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = ECTheme.colors.red,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = ECTheme.dimensions.sixteen,
                    top = ECTheme.dimensions.four
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomTextField(
        value = "",
        onValueChange = {},
        label = "email",
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password") }
    )
}
