package com.hoy.ecommercecompose.domain.usecase

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String,
        password: String,
        address: String
    ): Flow<Resource<Boolean>> {
        return firebaseAuthRepositoryImpl.createUserWithEmailAndPassword(
            name = name,
            surname = surname,
            email = email,
            password = password,
            address = address
        )
    }
}