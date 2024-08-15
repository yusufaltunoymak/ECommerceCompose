package com.hoy.ecommercecompose.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class ProductEntity(
    @ColumnInfo("userId")
    val id: String,
    @PrimaryKey
    @ColumnInfo("productId")
    val productId: Int,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("count")
    val count: Int,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("imageOne")
    val imageOne: String,
    @ColumnInfo("imageThree")
    val imageThree: String,
    @ColumnInfo("imageTwo")
    val imageTwo: String,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("rate")
    val rate: Double,
    @ColumnInfo("salePrice")
    val salePrice: Double,
    @ColumnInfo("saleState")
    val saleState: Boolean,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("isFavorite")
    val isFavorite: Boolean,
    @ColumnInfo("quantity")
    val quantity: Int
)
