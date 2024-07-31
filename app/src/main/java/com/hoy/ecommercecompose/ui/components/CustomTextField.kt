package com.hoy.ecommercecompose.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    leadingIcon: @Composable () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { leadingIcon() },
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.Gray.copy(alpha = 0.1f),
            focusedIndicatorColor = LocalColors.current.primary.copy(alpha = 0.3f),  // Border color when focused
            unfocusedIndicatorColor = LocalColors.current.primary.copy(alpha = 0.3f),

            ),

        )
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    CustomTextField(value = "", onValueChange ={} , label = "email", leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password") }
    )
}