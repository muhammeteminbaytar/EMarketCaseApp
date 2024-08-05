package com.example.emarketcaseapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteProduct::class, CartProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
    abstract fun cartProductDao(): CartProductDao

}