package com.hoy.ecommercecompose.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column {
        Text(text = "Profile Page")
        TextButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login")
            }
        ) {
            Text(text = "log out")
        }
    }
}
