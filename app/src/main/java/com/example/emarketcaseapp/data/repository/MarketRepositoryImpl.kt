package com.example.emarketcaseapp.data.repository

import com.example.emarketcaseapp.data.remote.MarketAPI
import com.example.emarketcaseapp.data.remote.dto.ProductDto
import com.example.emarketcaseapp.domain.repository.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(private val api: MarketAPI) : MarketRepository {
    override suspend fun getProducts(): ProductDto {
        return api.getProducts()
    }
}