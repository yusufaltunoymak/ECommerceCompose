package com.hoy.ecommercecompose.usecase.auth

import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.usecase.auth.CreateUserWithEmailAndPasswordUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CreateUserWithEmailAndPasswordUseCaseTest {
    @Mock
    lateinit var mockFirebaseAuthRepository: FirebaseAuthRepository

    private lateinit var createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        createUserWithEmailAndPasswordUseCase =
            CreateUserWithEmailAndPasswordUseCase(mockFirebaseAuthRepository)
    }

    @Test
    fun `test createUserWithEmailAndPasswordUseCase creates user successfully`() {
    }

    @Test
    fun `test createUserWithEmailAndPasswordUseCase handles exception`() {
    }
}
