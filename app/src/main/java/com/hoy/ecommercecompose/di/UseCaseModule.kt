package com.hoy.ecommercecompose.di

import com.hoy.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import com.hoy.ecommercecompose.data.repository.ProductRepositoryImpl
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.hoy.ecommercecompose.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindFirebaseRepository(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
    ): FirebaseAuthRepository
}

