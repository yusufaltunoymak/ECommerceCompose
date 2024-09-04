package com.hoy.ecommercecompose.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.User
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<Resource<Unit>> = flow {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val createdUser = User(
                email = email,
                id = result.user!!.uid,
                name = name,
                surname = surname,
                address = address
            )
            firestore.collection("Users").document(createdUser.id!!).set(createdUser).await()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Unit>> = flow {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun getUserInformation(): Flow<Resource<User>> = flow {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                val document = firestore.collection("Users").document(currentUser.uid).get().await()
                val user = document.toObject(User::class.java)
                if (user != null) {
                    emit(Resource.Success(user))
                } else {
                    emit(Resource.Error("User data is null"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }
        } else {
            emit(Resource.Error("No user is currently logged in"))
        }
    }

    override fun getUserId(): String {
        return firebaseAuth.currentUser?.uid.orEmpty()
    }

    override suspend fun updateUserInformation(user: User): Flow<Resource<Unit>> = flow {
        try {
            firestore.collection("Users").document(user.id!!).set(user).await()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Flow<Resource<Unit>> {
        return flow {
            val user = firebaseAuth.currentUser
            if (user != null) {
                try {
                    val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
                    user.reauthenticate(credential).await()
                    user.updatePassword(newPassword).await()
                    emit(Resource.Success(Unit))
                } catch (e: Exception) {
                    emit(Resource.Error(e.message ?: "An unknown error occurred"))
                }
            } else {
                emit(Resource.Error("No user is currently logged in"))
            }
        }
    }

    override suspend fun signOut(): Flow<Resource<Unit>> = flow {
        try {
            firebaseAuth.signOut()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }
}
