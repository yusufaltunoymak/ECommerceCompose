package com.hoy.ecommercecompose.ui.sendmail

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SendMailViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _sendMailUiState: MutableStateFlow<SendMailContract.SendMailUiState> =
        MutableStateFlow(SendMailContract.SendMailUiState())
    val sendMailUiState = _sendMailUiState.asStateFlow()

    fun onAction(action: SendMailContract.SendMailUiAction) {
        when (action) {
            is SendMailContract.SendMailUiAction.SendEmailAction -> {
                _sendMailUiState.value = _sendMailUiState.value.copy(email = action.email)
            }

            SendMailContract.SendMailUiAction.SendMail -> sendMail(_sendMailUiState.value.email)
        }
    }

    private fun sendMail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Mail gönderildi")
                } else {
                    println("Mail gönderilemedi")
                }
            }
    }
}
