package com.hoy.ecommercecompose.data.source.local.payment

import androidx.room.Embedded
import androidx.room.Relation

data class PaymentWithProducts(
    @Embedded val payment: PaymentEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val orderedProducts: List<OrderedProductEntity>
)
