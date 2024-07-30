package com.hoy.ecommercecompose.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.User
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth,
    private val firestore : FirebaseFirestore
) : FirebaseAuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        surname: String,
        address : String
    ): Flow<Resource<Boolean>> {
        val userReference = firestore.collection("Users")
        return callbackFlow {
            trySend(Resource.Loading())
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val createdUser = User(
                        email = email,
                        id = it.user!!.uid,
                        name = name,
                        surname = surname,
                        address = address
                    )
                    userReference.document(createdUser.id!!).set(createdUser)
                        .addOnSuccessListener {
                            trySend(Resource.Success(true))
                        }
                        .addOnFailureListener {
                            trySend(Resource.Error(it.message.toString()))
                        }
                }
                    .addOnFailureListener {
                        trySend(Resource.Error(it.message.toString()))
                    }
            }
            catch (e : FirebaseException) {
                trySend(Resource.Error(e.message.toString()))
            }
            awaitClose()
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    trySend(Resource.Success(true))
                }
                    .addOnFailureListener {
                        trySend(Resource.Error(it.message.toString()))
                    }
            }
            catch (e : FirebaseException) {
                trySend(Resource.Error(e.message.toString()))
            }
        }
    }

}