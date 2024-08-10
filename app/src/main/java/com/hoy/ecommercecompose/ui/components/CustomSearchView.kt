package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun CustomSearchView(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String,
    onCloseClicked: () -> Unit,
    onSearchClick: () -> Unit,
    onSortClick: () -> Unit
) {
    val containerColor = Color.White
    val indicatorColor = LocalColors.current.primary.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onSearchClick() // Trigger page navigation on click
            }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                onTextChange(newText) // Update text as the user types
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
            leadingIcon = { // Use leadingIcon to place the search icon at the start
                IconButton(onClick = {
                    onSearchClick() // Search icon click behavior
                }) {
                    Icon(
                        imageVector = Icons.Default.Search, // Use Search icon
                        contentDescription = "Search",
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotBlank()) {
                        onCloseClicked()
                    } else {
                        onSortClick()
                    }
                }) {
                    Icon(
                        imageVector = if (text.isNotBlank()) Icons.Default.Clear else Icons.AutoMirrored.Filled.List,
                        contentDescription = if (text.isNotBlank()) "Clear" else "Sort",
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(), // Make the OutlinedTextField fill the width
            shape = RoundedCornerShape(8.dp),
            singleLine = true // Keep it single line
        )
    }
}

