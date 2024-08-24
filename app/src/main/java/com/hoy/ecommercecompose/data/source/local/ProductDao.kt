package com.hoy.ecommercecompose.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hoy.ecommercecompose.data.source.local.payment.model.PaymentEntity
import com.hoy.ecommercecompose.data.source.local.payment.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products_table WHERE userId = :userId")
    fun getCartProducts(userId: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCartProduct(product: ProductEntity)

    @Query("DELETE FROM products_table WHERE productId = :productId")
    suspend fun deleteFromCartProduct(productId: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCartProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPaymentDetails(paymentEntity: PaymentEntity)

    @Query("SELECT * FROM payment_table WHERE userId = :userId")
    fun getUserOrders(userId: String): Flow<List<PaymentEntity>>

    @Query("DELETE FROM products_table WHERE userId = :userId")
    suspend fun clearCart(userId: String)


    @Transaction
    suspend fun processOrder(userId: String, paymentEntity: PaymentEntity) {
        addPaymentDetails(paymentEntity)
        clearCart(userId)
    }

}
