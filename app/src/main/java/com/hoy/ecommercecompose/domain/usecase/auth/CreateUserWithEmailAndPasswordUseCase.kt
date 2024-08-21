package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String,
        password: String,
        address: String
    ): Resource<Unit> {
        return firebaseAuthRepository.createUserWithEmailAndPassword(
            name = name,
            surname = surname,
            email = email,
            password = password,
            address = address
        )
    }
}
