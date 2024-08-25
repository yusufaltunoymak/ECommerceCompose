package com.hoy.ecommercecompose.ui.sendmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hoy.ecommercecompose.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMailViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState: MutableStateFlow<SendMailContract.SendMailUiState> =
        MutableStateFlow(SendMailContract.SendMailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SendMailContract.UiEffect>() }
    val uiEffect: Flow<SendMailContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: SendMailContract.SendMailUiAction) {
        when (action) {
            is SendMailContract.SendMailUiAction.SendEmailAction -> {
                updateUiState { copy(email = action.email) }
            }

            SendMailContract.SendMailUiAction.SendMail -> sendMail(_uiState.value.email)
        }
    }

    private fun sendMail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        emitUiEffect(SendMailContract.UiEffect.ShowToast(R.string.mail_sent_success))
                    } else {
                        emitUiEffect(SendMailContract.UiEffect.ShowToast(R.string.mail_sent_failure))
                    }
                }
            }
    }

    private fun updateUiState(block: SendMailContract.SendMailUiState.() -> SendMailContract.SendMailUiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: SendMailContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}
