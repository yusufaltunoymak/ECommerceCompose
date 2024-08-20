package com.hoy.ecommercecompose.di

import android.content.Context
import com.hoy.ecommercecompose.data.datasources.AssetCityDataSource
import com.hoy.ecommercecompose.data.datasources.CityDataSource
import com.hoy.ecommercecompose.data.datasources.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCityDataSource(
        @ApplicationContext context: Context
    ): CityDataSource {
        return AssetCityDataSource(context)
    }

    @Provides
    @Singleton
    fun provideCityRepository(
        cityDataSource: CityDataSource
    ): CityRepository {
        return CityRepository(cityDataSource)
    }
}
