package com.hoy.ecommercecompose.di

import android.content.Context
import com.hoy.ecommercecompose.common.Constants.BASE_URL
import com.hoy.ecommercecompose.data.source.remote.ApiService
import com.hoy.ecommercecompose.domain.repository.ConnectivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectivityRepository(@ApplicationContext context: Context): ConnectivityRepository {
        return ConnectivityRepository(context)
    }
}
