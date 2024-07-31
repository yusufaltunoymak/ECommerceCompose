package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource

interface FirebaseAuthRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        address: String
    ): Resource<Unit>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Resource<Unit>
}
