package com.example.emarketcaseapp.data.remote.dto

import com.example.emarketcaseapp.domain.model.Product

class ProductDto : ArrayList<ProductDtoItem>()

fun ProductDto.toListProduct(): List<Product> {
    return this.map { product ->
        Product(product.brand, product.createdAt, product.description, product.id, product.image, product.model, product.name, product.price)
    }
}
