package com.hoy.ecommercecompose.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM products_table")
    suspend fun getFavoriteProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(product: ProductEntity)

    @Delete
    suspend fun removeFavoriteProduct(product: ProductEntity)
}