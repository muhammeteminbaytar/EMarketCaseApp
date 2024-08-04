package com.example.emarketcaseapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey val productId: Int
)