package com.example.emarketcaseapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProduct(
    @PrimaryKey val productId: String,
    val quantity: Int
)