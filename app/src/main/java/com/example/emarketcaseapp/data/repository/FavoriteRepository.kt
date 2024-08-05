package com.example.emarketcaseapp.data.repository

import com.example.emarketcaseapp.data.local.FavoriteProduct
import com.example.emarketcaseapp.data.local.FavoriteProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) {
    suspend fun addFavorite(productId: String) {
        favoriteProductDao.insertFavoriteProduct(FavoriteProduct(productId))
    }

    suspend fun removeFavorite(productId: String) {
        favoriteProductDao.deleteFavoriteProduct(FavoriteProduct(productId))
    }

    fun isFavorite(productId: String): Flow<Boolean> {
        return favoriteProductDao.isFavorite(productId)
    }

    fun getAllFavoriteIds(): Flow<List<String>> {
        return favoriteProductDao.getAllFavoriteProductIds()
    }
    fun getFavoriteCount(): Flow<Int>{
        return favoriteProductDao.getFavoriteProductCount()
    }
}
