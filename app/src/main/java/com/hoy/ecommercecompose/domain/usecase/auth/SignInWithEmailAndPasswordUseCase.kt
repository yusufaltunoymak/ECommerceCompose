package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> = flow {
        firebaseAuthRepository.signInWithEmailAndPassword(
            email = email,
            password = password
        ).collect() { resource ->
            emit(resource)
        }
    }
}
