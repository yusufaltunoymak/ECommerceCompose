package com.hoy.ecommercecompose.domain.usecase

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        surname: String,
        address: String
    ): Flow<Resource<Boolean>> {
        return firebaseAuthRepositoryImpl.createUserWithEmailAndPassword(
            email = email,
            password = password,
            name = name,
            surname = surname,
            address = address
        )
    }
}