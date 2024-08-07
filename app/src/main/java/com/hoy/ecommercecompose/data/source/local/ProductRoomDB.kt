package com.hoy.ecommercecompose.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductRoomDB : RoomDatabase() {
    abstract val productDao: ProductDao
}