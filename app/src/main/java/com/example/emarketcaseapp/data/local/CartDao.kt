package com.example.emarketcaseapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(cardProduct: FavoriteProduct)

    @Delete
    suspend fun deleteCartProduct(favoriteProduct: FavoriteProduct)

}