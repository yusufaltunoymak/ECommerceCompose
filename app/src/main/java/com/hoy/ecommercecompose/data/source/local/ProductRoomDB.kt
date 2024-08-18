package com.hoy.ecommercecompose.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoy.ecommercecompose.data.source.local.payment.OrderedProductEntity
import com.hoy.ecommercecompose.data.source.local.payment.PaymentEntity

@Database(
    entities = [ProductEntity::class, PaymentEntity::class, OrderedProductEntity::class],
    version = 5,
    exportSchema = false
)
abstract class ProductRoomDB : RoomDatabase() {
    abstract val productDao: ProductDao
}
