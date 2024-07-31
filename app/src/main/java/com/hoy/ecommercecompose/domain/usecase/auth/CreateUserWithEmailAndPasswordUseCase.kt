package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String,
        password: String,
        address: String
    ): Resource<Unit> {
        return firebaseAuthRepositoryImpl.createUserWithEmailAndPassword(
            name = name,
            surname = surname,
            email = email,
            password = password,
            address = address
        )
    }
}