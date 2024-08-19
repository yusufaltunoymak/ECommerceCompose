package com.hoy.ecommercecompose.ui.login.google

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.data.source.remote.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUiClient @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val oneTapClient = Identity.getSignInClient(context)
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken

        return if (googleIdToken != null) {
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            try {
                val authResult = auth.signInWithCredential(googleCredentials).await()
                val user = authResult.user
                SignInResult(
                    data = user?.run {
                        User(
                            id = uid,
                            email = email ?: ""
                        )
                    },
                    errorMessage = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                SignInResult(
                    data = null,
                    errorMessage = e.message
                )
            }
        } else {
            SignInResult(
                data = null,
                errorMessage = "Google ID Token was null"
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): User? = auth.currentUser?.run {
        User(
            id = uid,
            email = email,
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
