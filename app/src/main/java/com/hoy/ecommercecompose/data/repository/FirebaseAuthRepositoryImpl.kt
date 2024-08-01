package com.hoy.ecommercecompose.data.repository

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.User
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseAuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        address: String
    ): Resource<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val createdUser = User(
                email = email,
                id = result.user!!.uid,
                name = name,
                surname = surname,
                address = address
            )
            firestore.collection("Users").document(createdUser.id!!).set(createdUser).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }


    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(Unit)
        } catch (e: FirebaseException) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getUserInformation(): Resource<User> {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null) {
            try {
                val document = firestore.collection("Users").document(currentUser.uid).get().await()
                val user = document.toObject(User::class.java)
                if (user != null) {
                    Resource.Success(user)
                } else {
                    Resource.Error("User data is null")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An unknown error occurred")
            }
        } else {
            Resource.Error("No user is currently logged in")
        }
    }
}