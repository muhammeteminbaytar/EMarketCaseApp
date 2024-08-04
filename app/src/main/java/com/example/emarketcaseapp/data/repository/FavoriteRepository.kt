package com.example.emarketcaseapp.data.repository

import com.example.emarketcaseapp.data.local.FavoriteProduct
import com.example.emarketcaseapp.data.local.FavoriteProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) {
    suspend fun addFavorite(productId: Int) {
        favoriteProductDao.insertFavoriteProduct(FavoriteProduct(productId))
    }

    suspend fun removeFavorite(productId: Int) {
        favoriteProductDao.deleteFavoriteProduct(FavoriteProduct(productId))
    }

    fun isFavorite(productId: Int): Flow<Boolean> {
        return favoriteProductDao.getFavoriteProductById(productId).map { it != null }
    }

    fun getAllFavoriteIds(): Flow<List<Int>> {
        return favoriteProductDao.getAllFavoriteProductIds()
    }
}
