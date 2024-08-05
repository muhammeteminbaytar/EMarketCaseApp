package com.example.emarketcaseapp.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)

    @Delete
    suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE productId = :productId)")
    fun isFavorite(productId: String): Flow<Boolean>

    @Query("SELECT productId FROM favorite_products")
    fun getAllFavoriteProductIds(): Flow<List<String>>
}