package com.hoy.ecommercecompose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField

@Composable
fun RegisterScreen(){

    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

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
            value = name.value,
            onValueChange = { email.value = it },
            label = "Name",
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
        )
        CustomTextField(
            value = surname.value,
            onValueChange = { email.value = it },
            label = "Surname",
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
        )
        CustomTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = "Email",
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
        )
        CustomTextField(
            value = password.value,
            onValueChange = { email.value = it },
            label = "Password",
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(24.dp))


        CustomButton(text = "Register", onClick = {  })
    }
}

@Preview(showBackground = true)
@Composable
fun Previeww(){
    RegisterScreen(
    )
}