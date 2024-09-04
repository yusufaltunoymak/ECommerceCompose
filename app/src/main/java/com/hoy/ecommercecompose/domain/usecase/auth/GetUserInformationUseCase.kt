package com.hoy.ecommercecompose.domain.usecase.auth

import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.data.source.remote.model.User
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<User>> = flow {
        repository.getUserInformation().collect { resource ->
            emit(resource)
        }
    }
}
