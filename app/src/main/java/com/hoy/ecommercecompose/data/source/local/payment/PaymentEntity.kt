package com.hoy.ecommercecompose.data.source.local.payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_table")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    @ColumnInfo("userId")
    val userId: String,
    @ColumnInfo("cardNumber")
    val cardNumber: String,
    @ColumnInfo("cardHolderName")
    val cardHolderName: String,
    @ColumnInfo("expirationDate")
    val expirationDate: String,
    @ColumnInfo("amount")
    val amount: Double,
    @ColumnInfo("city")
    val city: String,
    @ColumnInfo("district")
    val district: String,
    @ColumnInfo("fullAddress")
    val fullAddress: String
)
