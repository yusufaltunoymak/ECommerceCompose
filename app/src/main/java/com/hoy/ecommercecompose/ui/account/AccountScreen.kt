package com.hoy.ecommercecompose.ui.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.theme.LocalColors
import com.hoy.ecommercecompose.ui.theme.LocalDimensions
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountScreen(
    uiEffect: Flow<AccountContract.UiEffect>,
    uiState: AccountContract.UiState,
    onAction: (AccountContract.UiAction) -> Unit,
    onBackClick: () -> Unit,
) {

    var name by remember { mutableStateOf(uiState.currentUser?.name.orEmpty()) }
    var surname by remember { mutableStateOf(uiState.currentUser?.surname.orEmpty()) }
    var email by remember { mutableStateOf(uiState.currentUser?.email.orEmpty()) }
    var address by remember { mutableStateOf(uiState.currentUser?.address.orEmpty()) }
    var isSaveEnabled by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    else -> {}
                }
            }
        }
    }

    if (uiState.currentUser != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = LocalDimensions.current.sixteen,
                    vertical = LocalDimensions.current.eight
                )
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(LocalDimensions.current.fortyEight)
                            .border(
                                BorderStroke(
                                    LocalDimensions.current.one,
                                    LocalColors.current.primary
                                ),
                                shape = RoundedCornerShape(LocalDimensions.current.twelve)
                            )
                    ) {
                        Icon(
                            modifier = Modifier.size(LocalDimensions.current.forty),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.account_screen_title),
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(
                        onClick = {
                            if (isEditing) {
                                // Handle save action
                                isEditing = false
                            } else {
                                isEditing = true
                            }
                        },
                        modifier = Modifier
                            .size(LocalDimensions.current.fortyEight)
                            .border(
                                BorderStroke(
                                    LocalDimensions.current.one,
                                    LocalColors.current.primary
                                ),
                                shape = RoundedCornerShape(LocalDimensions.current.twelve)
                            )
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(LocalDimensions.current.twenty))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LocalDimensions.current.four),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = uiState.currentUser.name!!,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LocalColors.current.primary,
                            unfocusedBorderColor = LocalColors.current.primary,
                            focusedLabelColor = LocalColors.current.secondary,
                            unfocusedLabelColor = LocalColors.current.secondary,
                        ),
                        enabled = isEditing,
                        onValueChange = {
                            name = it
                            isSaveEnabled = true
                        },
                        label = { Text(stringResource(id = R.string.name_label), style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = LocalDimensions.current.eight)
                    )

                    OutlinedTextField(
                        value = uiState.currentUser.surname!!,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LocalColors.current.primary,
                            unfocusedBorderColor = LocalColors.current.primary,
                            focusedLabelColor = LocalColors.current.secondary,
                            unfocusedLabelColor = LocalColors.current.secondary,
                        ),
                        enabled = isEditing,
                        onValueChange = {
                            surname = it
                            isSaveEnabled = true
                        },
                        label = { Text(stringResource(id = R.string.surname_label), style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = LocalDimensions.current.eight)
                    )
                }

                OutlinedTextField(
                    value = uiState.currentUser.email!!,
                    enabled = isEditing,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LocalColors.current.primary,
                        unfocusedBorderColor = LocalColors.current.primary,
                        focusedLabelColor = LocalColors.current.secondary,
                        unfocusedLabelColor = LocalColors.current.secondary,
                    ),
                    onValueChange = {
                        email = it
                        isSaveEnabled = true
                    },
                    label = { Text(stringResource(id = R.string.email_label), style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LocalDimensions.current.four)
                )

                OutlinedTextField(
                    value = uiState.currentUser.address!!,
                    enabled = isEditing,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LocalColors.current.primary,
                        unfocusedBorderColor = LocalColors.current.primary,
                        focusedLabelColor = LocalColors.current.secondary,
                        unfocusedLabelColor = LocalColors.current.secondary,
                    ),
                    onValueChange = {
                        address = it
                        isSaveEnabled = true
                    },
                    label = { Text(stringResource(id = R.string.address_label), style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LocalDimensions.current.four)
                )

                Spacer(modifier = Modifier.height(LocalDimensions.current.twenty))

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier
                        .padding(horizontal = LocalDimensions.current.sixteen)
                        .fillMaxWidth()
                )
                MenuItem(iconId = R.drawable.ic_order, title = stringResource(id = R.string.my_order), onClick = { })
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier
                        .padding(horizontal = LocalDimensions.current.sixteen)
                        .fillMaxWidth()
                )

                MenuItem(iconId = R.drawable.ic_payment, title = stringResource(id = R.string.payment_methods), onClick = { })
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier
                        .padding(horizontal = LocalDimensions.current.sixteen)
                        .fillMaxWidth()
                )
                MenuItem(iconId = R.drawable.circle_lock, title = stringResource(id = R.string.change_password), onClick = { })
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier
                        .padding(horizontal = LocalDimensions.current.sixteen)
                        .fillMaxWidth()
                )
                MenuItem(iconId = R.drawable.ic_logout, title = stringResource(id = R.string.log_out), onClick = { }, textColor = Color.Red)
            }
        }
    } else {
        Text(
            text = stringResource(id = R.string.connection_error),
            modifier = Modifier.fillMaxSize(),
            color = Color.Red,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

@Composable
fun MenuItem(iconId: Int, title: String, onClick: () -> Unit, textColor: Color = Color.Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = LocalDimensions.current.sixteen)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(LocalDimensions.current.twentyFour)
        )
        Spacer(modifier = Modifier.width(LocalDimensions.current.sixteen))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}