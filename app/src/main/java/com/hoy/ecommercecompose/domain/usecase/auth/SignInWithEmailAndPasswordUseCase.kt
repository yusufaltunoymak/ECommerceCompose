package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        return firebaseAuthRepositoryImpl.signInWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}