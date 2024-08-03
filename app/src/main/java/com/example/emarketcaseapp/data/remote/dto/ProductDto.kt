package com.example.emarketcaseapp.data.remote.dto

import com.example.emarketcaseapp.domain.model.Product

data class ProductDto (
    val Products: List<Product>
)

fun ProductDto.toListProduct(): List<Product> {
    return Products.map { product ->
        Product(product.brand, product.createdAt, product.id, product.image, product.model, product.name, product.price)
    }
}