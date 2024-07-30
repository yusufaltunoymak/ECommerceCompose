package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        address: String
    ): Flow<Resource<Boolean>>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<Boolean>>
}