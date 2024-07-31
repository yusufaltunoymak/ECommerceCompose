package com.hoy.ecommercecompose.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.hoy.ecommercecompose.ui.login.google.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebase() : Firebase {
        return Firebase
    }

    @Provides
    @Singleton
    fun provideFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideSignInClient(context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    fun provideGoogleAuthUiClient(
        context: Context,
        signInClient: SignInClient
    ): GoogleAuthUiClient {
        return GoogleAuthUiClient(context, signInClient)
    }
}