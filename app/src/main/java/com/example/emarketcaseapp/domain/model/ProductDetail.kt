package com.example.emarketcaseapp.domain.model

import java.io.Serializable

class ProductDetail (
    val brand: String,
    val createdAt: String,
    val description: String,
    val id: String,
    val image: String,
    val model: String,
    val name: String,
    val price: String
) : Serializable