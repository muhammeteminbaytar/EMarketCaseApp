package com.example.emarketcaseapp.di

import android.content.Context
import androidx.room.Room
import com.example.emarketcaseapp.data.local.AppDatabase
import com.example.emarketcaseapp.data.local.CartProductDao
import com.example.emarketcaseapp.data.local.FavoriteProductDao
import com.example.emarketcaseapp.data.remote.MarketAPI
import com.example.emarketcaseapp.data.repository.CartRepository
import com.example.emarketcaseapp.data.repository.FavoriteRepository
import com.example.emarketcaseapp.data.repository.MarketRepositoryImpl
import com.example.emarketcaseapp.domain.repository.MarketRepository
import com.example.emarketcaseapp.domain.use_case.toggle_favorite.ToggleFavoriteUseCase
import com.example.emarketcaseapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMarketApi() : MarketAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarketAPI::class.java)
    }
    @Singleton
    @Provides
    fun providesMarketRepository(api : MarketAPI) : MarketRepository {
        return MarketRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavoriteProductDao(database: AppDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: FavoriteProductDao): FavoriteRepository {
        return FavoriteRepository(dao)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(repository: FavoriteRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(repository)
    }

    @Provides
    fun provideCartProductDao(database: AppDatabase): CartProductDao {
        return database.cartProductDao()
    }

    @Provides
    @Singleton
    fun provideCrtRepository(dao: CartProductDao): CartRepository {
        return CartRepository(dao)
    }
}