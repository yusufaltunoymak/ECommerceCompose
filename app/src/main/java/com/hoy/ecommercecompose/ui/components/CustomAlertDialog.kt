package com.hoy.ecommercecompose.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.util.FirebaseError.EMAIL_FORMAT_ERROR
import com.hoy.ecommercecompose.util.FirebaseError.EMPTY_FIELD_ERROR
import com.hoy.ecommercecompose.util.FirebaseError.PASSWORD_INVALID_ERROR

@Composable
fun CustomAlertDialog(
    errorMessage: String,
    onDismiss: () -> Unit,
    confirmButtonClickListener: (() -> Unit)? = null
) {
    val message = when (errorMessage) {
        EMAIL_FORMAT_ERROR -> stringResource(id = R.string.email_format_error)
        PASSWORD_INVALID_ERROR -> stringResource(id = R.string.password_invalid_error)
        EMPTY_FIELD_ERROR -> stringResource(id = R.string.empty_field_error)
        else -> errorMessage
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.validation_error)) },
        text = { Text(text = message) },
        confirmButton = {
            CustomButton(text = stringResource(id = R.string.ok_button), onClick = {
                confirmButtonClickListener?.invoke()
                onDismiss()
            })
        }
    )
}
