package com.hoy.ecommercecompose.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products_table WHERE userId = :userId")
    fun getCartProducts(userId: String):Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCartProduct(product: ProductEntity)

    @Query("DELETE FROM products_table WHERE productId = :productId")
    suspend fun deleteFromCartProduct(productId: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCartProduct(product: ProductEntity)
}