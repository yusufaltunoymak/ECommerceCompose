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
import androidx.compose.ui.tooling.preview.Preview
import com.hoy.ecommercecompose.ui.theme.ECTheme

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    contentColor: Color = ECTheme.colors.primary
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = ECTheme.dimensions.eight)
            .height(ECTheme.dimensions.fiftySix)
            .fillMaxWidth(),
        border = BorderStroke(ECTheme.dimensions.one, contentColor),
        shape = RoundedCornerShape(ECTheme.dimensions.sixteen),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = ECTheme.colors.white
        )

    ) {
        Text(text = text, fontSize = ECTheme.typography.medium, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomOutlineButtonPreview() {
    CustomOutlinedButton(
        text = "Button",
        onClick = { },
        contentColor = ECTheme.colors.primary
    )
}
