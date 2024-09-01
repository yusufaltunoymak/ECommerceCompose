package com.hoy.ecommercecompose.ui.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.theme.ECTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountScreen(
    uiEffect: Flow<AccountContract.UiEffect>,
    uiState: AccountContract.UiState,
    onAction: (AccountContract.UiAction) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToPassword: () -> Unit,
    onNavigateToNotifications: () -> Unit,
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
                    horizontal = ECTheme.dimensions.sixteen,
                    vertical = ECTheme.dimensions.eight
                )
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.account_screen_title),
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(
                        onClick = {
                            if (isEditing) {
                                val updatedUser = uiState.currentUser.copy(
                                    name = name,
                                    surname = surname,
                                    email = email,
                                    address = address
                                )
                                onAction(AccountContract.UiAction.SaveUserInformation(updatedUser))
                                isEditing = false
                                isSaveEnabled = false
                            } else {
                                isEditing = true
                            }
                        },
                        modifier = Modifier
                            .size(ECTheme.dimensions.fortyEight)
                            .border(
                                BorderStroke(
                                    ECTheme.dimensions.one,
                                    ECTheme.colors.primary
                                ),
                                shape = RoundedCornerShape(ECTheme.dimensions.twelve)
                            )
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(ECTheme.dimensions.twenty))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = ECTheme.dimensions.four),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = name,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ECTheme.colors.primary,
                            unfocusedBorderColor = ECTheme.colors.primary,
                            focusedLabelColor = ECTheme.colors.secondary,
                            unfocusedLabelColor = ECTheme.colors.secondary,
                        ),
                        enabled = isEditing,
                        onValueChange = {
                            name = it
                            isSaveEnabled = true
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.name_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = ECTheme.dimensions.eight)
                    )

                    OutlinedTextField(
                        value = surname,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ECTheme.colors.primary,
                            unfocusedBorderColor = ECTheme.colors.primary,
                            focusedLabelColor = ECTheme.colors.secondary,
                            unfocusedLabelColor = ECTheme.colors.secondary,
                        ),
                        enabled = isEditing,
                        onValueChange = {
                            surname = it
                            isSaveEnabled = true
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.surname_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = ECTheme.dimensions.eight)
                    )
                }

                OutlinedTextField(
                    value = email,
                    enabled = isEditing,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ECTheme.colors.primary,
                        unfocusedBorderColor = ECTheme.colors.primary,
                        focusedLabelColor = ECTheme.colors.secondary,
                        unfocusedLabelColor = ECTheme.colors.secondary,
                    ),
                    onValueChange = {
                        email = it
                        isSaveEnabled = true
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.email_label),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = ECTheme.dimensions.four)
                )

                OutlinedTextField(
                    value = address,
                    enabled = isEditing,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ECTheme.colors.primary,
                        unfocusedBorderColor = ECTheme.colors.primary,
                        focusedLabelColor = ECTheme.colors.secondary,
                        unfocusedLabelColor = ECTheme.colors.secondary,
                    ),
                    onValueChange = {
                        address = it
                        isSaveEnabled = true
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.address_label),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = ECTheme.dimensions.four)
                )

                Spacer(modifier = Modifier.height(ECTheme.dimensions.twenty))

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = ECTheme.dimensions.sixteen)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
                MenuItem(
                    iconId = R.drawable.ic_notifications,
                    title = stringResource(id = R.string.notification),
                    onClick = {
                        onNavigateToNotifications()
                    }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = ECTheme.dimensions.sixteen)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )

                MenuItem(
                    iconId = R.drawable.ic_payment,
                    title = stringResource(id = R.string.payment_methods),
                    onClick = { }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = ECTheme.dimensions.sixteen)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
                MenuItem(
                    iconId = R.drawable.ic_circle_lock,
                    title = stringResource(
                        id = R.string.change_password
                    ),
                    onClick = {
                        onNavigateToPassword()
                    }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = ECTheme.dimensions.sixteen)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
                MenuItem(iconId = R.drawable.ic_logout, title = stringResource(id = R.string.log_out), onClick = {
                    FirebaseAuth.getInstance().signOut()
                    onNavigateToLogin()
                }, textColor = ECTheme.colors.red)
            }
        }
    } else {
        Text(
            text = stringResource(id = R.string.connection_error),
            modifier = Modifier.fillMaxSize(),
            color = ECTheme.colors.red,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

@Composable
fun MenuItem(
    iconId: Int,
    title: String,
    onClick: () -> Unit,
    textColor: Color = ECTheme.colors.black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = ECTheme.dimensions.sixteen)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(ECTheme.dimensions.twentyFour)
        )
        Spacer(modifier = Modifier.width(ECTheme.dimensions.sixteen))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}
