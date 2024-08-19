package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        return firebaseAuthRepository.signInWithEmailAndPassword(
            email = email,
            password = password
        )
    }
}
