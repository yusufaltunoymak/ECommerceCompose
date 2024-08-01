package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.model.User
import com.hoy.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val repository: FirebaseAuthRepositoryImpl
) {
    suspend operator fun invoke(): Resource<User> {
        return repository.getUserInformation()
    }
}