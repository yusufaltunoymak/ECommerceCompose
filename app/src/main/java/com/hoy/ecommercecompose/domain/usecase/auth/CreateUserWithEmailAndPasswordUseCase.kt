package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<Resource<Unit>> = flow {
        firebaseAuthRepository.createUserWithEmailAndPassword(
            name = name,
            surname = surname,
            email = email,
            password = password,
            address = address
        ).collect() { resource ->
            emit(resource)
        }
    }
}
