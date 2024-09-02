package com.hoy.ecommercecompose.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.User
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
            Resource.Loading
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
        } catch (e: Exception) {
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

    override fun getUserId(): String {
        return firebaseAuth.currentUser?.uid.orEmpty()
    }

    override suspend fun updateUserInformation(user: User): Resource<Unit> {
        return try {
            Resource.Loading
            firestore.collection("Users").document(user.id!!).set(user).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
    override suspend fun changePassword(currentPassword: String, newPassword: String): Resource<Unit> {
        val user = firebaseAuth.currentUser
        return if (user != null) {
            try {
                val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
                user.reauthenticate(credential).await()
                user.updatePassword(newPassword).await()
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An unknown error occurred")
            }
        } else {
            Resource.Error("No user is currently logged in")
        }
    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }
}
