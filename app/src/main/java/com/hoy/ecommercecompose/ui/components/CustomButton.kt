package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    colors: Color = LocalColors.current.primary,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(56.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16),
        colors = ButtonDefaults.buttonColors(containerColor = colors),
        enabled = enabled
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CustomButton(text = "asdas", onClick = { /*TODO*/ })
}
