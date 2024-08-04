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

    @Query("SELECT * FROM favorite_products WHERE productId = :productId LIMIT 1")
    fun getFavoriteProductById(productId: Int): Flow<FavoriteProduct?>

    @Query("SELECT productId FROM favorite_products")
    fun getAllFavoriteProductIds(): Flow<List<Int>>
}