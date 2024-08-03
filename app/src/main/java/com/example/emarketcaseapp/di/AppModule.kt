package com.example.emarketcaseapp.di

import com.example.emarketcaseapp.data.remote.MarketAPI
import com.example.emarketcaseapp.data.repository.MarketRepositoryImpl
import com.example.emarketcaseapp.domain.repository.MarketRepository
import com.example.emarketcaseapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}