package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.User
import javax.inject.Inject

interface FirebaseAuthRepository  {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        address: String
    ): Resource<Unit>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Resource<Unit>

    suspend fun getUserInformation(): Resource<User>

    suspend fun getUserId(): String

}
