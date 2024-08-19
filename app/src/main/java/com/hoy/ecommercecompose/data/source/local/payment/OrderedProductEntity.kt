package com.hoy.ecommercecompose.data.source.local.payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ordered_products_table",
    foreignKeys = [
        ForeignKey(
            entity = PaymentEntity::class,
            parentColumns = ["orderId"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrderedProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("orderId")
    val orderId: Int,
    @ColumnInfo("productId")
    val productId: Int,
    @ColumnInfo("quantity")
    val quantity: Int,
    @ColumnInfo("price")
    val price: Double
)
