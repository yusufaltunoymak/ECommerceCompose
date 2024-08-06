package com.hoy.ecommercecompose.di

import android.content.Context
import androidx.room.Room
import com.hoy.ecommercecompose.data.source.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  RoomDbModule {

    @Provides
    @Singleton
    fun provideProductDataBase(@ApplicationContext context: Context)  : ProductRoomDB {
        return Room.databaseBuilder(
            context, ProductRoomDB::class.java, "product_database" )
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(productRoomDB: ProductRoomDB) = productRoomDB.productDao

}