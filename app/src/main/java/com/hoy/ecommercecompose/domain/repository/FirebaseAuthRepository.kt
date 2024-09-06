package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        address: String
    ): Flow<Resource<Unit>>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<Unit>>

    suspend fun getUserInformation(): Flow<Resource<User>>

    fun getUserId(): String

    suspend fun updateUserInformation(user: User): Flow<Resource<Unit>>

    suspend fun changePassword(currentPassword: String, newPassword: String): Flow<Resource<Unit>>

    suspend fun signOut(): Flow<Resource<Unit>>
}
