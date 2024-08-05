package com.example.emarketcaseapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(cartProduct: CartProduct)

    @Update
    suspend fun updateCartProduct(cartProduct: CartProduct)

    @Delete
    suspend fun deleteCartProduct(cartProduct: CartProduct)

    @Query("SELECT * FROM cart_products WHERE productId = :productId LIMIT 1")
    suspend fun getCartProductById(productId: String): CartProduct?

    @Query("SELECT * FROM cart_products")
    suspend fun getAllCartProducts(): List<CartProduct>
}