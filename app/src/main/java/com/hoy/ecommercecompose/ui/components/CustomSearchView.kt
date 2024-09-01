package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.noRippleClickable
import com.hoy.ecommercecompose.ui.theme.ECTheme

@Composable
fun CustomSearchView(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String,
    onCloseClicked: () -> Unit,
    onSearchClick: () -> Unit,
) {
    val containerColor = Color.White
    val indicatorColor = ECTheme.colors.primary.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ECTheme.dimensions.eight))
            .noRippleClickable {
                onSearchClick()
            }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                onTextChange(newText)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedIndicatorColor = indicatorColor,
                unfocusedIndicatorColor = indicatorColor,
            ),
            placeholder = {
                Text(
                    text = placeHolder,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Normal,
                )
            },
            leadingIcon = {
                IconButton(onClick = {
                    onSearchClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search),
                        modifier = Modifier.size(ECTheme.dimensions.twentyFour)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotBlank()) {
                        onCloseClicked()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier.size(ECTheme.dimensions.twentyFour)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(ECTheme.dimensions.eight),
            singleLine = true
        )
    }
}
