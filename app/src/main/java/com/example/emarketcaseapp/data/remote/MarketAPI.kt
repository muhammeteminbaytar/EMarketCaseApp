package com.example.emarketcaseapp.data.remote

import com.example.emarketcaseapp.data.remote.dto.ProductDto
import retrofit2.http.GET

interface MarketAPI {

    //https://5fc9346b2af77700165ae514.mockapi.io/products

    @GET("/products")
    suspend fun getProducts(): ProductDto

}