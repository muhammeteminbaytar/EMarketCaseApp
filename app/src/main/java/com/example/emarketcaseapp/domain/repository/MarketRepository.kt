package com.example.emarketcaseapp.domain.repository

import com.example.emarketcaseapp.data.remote.dto.ProductDto

interface MarketRepository {
    suspend fun getProducts() : ProductDto
}