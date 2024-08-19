package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    contentColor: Color = LocalColors.current.primary
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(56.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, contentColor),
        shape = RoundedCornerShape(16),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = Color.White
        )

    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

// @Preview(showBackground = true)
// @Composable
// fun CustomOutlineButtonPreview() {
//    CustomOutlinedButton(text = "Button", onClick = { /*TODO*/ })
// }
