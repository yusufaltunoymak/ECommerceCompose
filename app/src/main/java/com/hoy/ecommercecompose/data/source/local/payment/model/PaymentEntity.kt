package com.hoy.ecommercecompose.data.source.local.payment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_table")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("paymentId")
    val paymentId: Int = 0,
    @ColumnInfo("userId")
    val userId: String,
    @ColumnInfo("cardNumber")
    val cardNumber: String,
    @ColumnInfo("cardHolderName")
    val cardHolderName: String,
    @ColumnInfo("expirationDate")
    val expirationDate: String,
    @ColumnInfo("city")
    val city: String,
    @ColumnInfo("district")
    val district: String,
    @ColumnInfo("fullAddress")
    val fullAddress: String,
    @ColumnInfo("productId")
    val productId: Int,
    @ColumnInfo("imageOne")
    val imageOne: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("quantity")
    val quantity: Int,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("OrderDate")
    val orderDate: Long = System.currentTimeMillis(),
)