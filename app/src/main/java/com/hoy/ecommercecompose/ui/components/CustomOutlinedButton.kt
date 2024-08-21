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
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import com.hoy.ecommercecompose.ui.theme.LocalFontSizes

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    contentColor: Color = LocalColors.current.primary
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = LocalDimensions.current.eight)
            .height(LocalDimensions.current.fiftySix)
            .fillMaxWidth(),
        border = BorderStroke(LocalDimensions.current.one, contentColor),
        shape = RoundedCornerShape(LocalDimensions.current.sixteen),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = LocalColors.current.white
        )

    ) {
        Text(text = text, fontSize = LocalFontSizes.current.medium, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomOutlineButtonPreview() {
    CustomOutlinedButton(
        text = "Button",
        onClick = { },
        contentColor = LocalColors.current.primary
    )
}
