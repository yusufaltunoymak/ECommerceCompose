package com.hoy.ecommercecompose.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity

@Database(
    entities = [ProductEntity::class, PaymentEntity::class],
    version = 6,
    exportSchema = false
)
abstract class ProductRoomDB : RoomDatabase() {
    abstract val productDao: ProductDao
}
