package com.example.emarketcaseapp.presentation.adapter

import com.example.emarketcaseapp.domain.model.Product

interface OnProductClickListener {
    fun onProductClick(product: Product)
    fun onFavoriteClick(product: Product)
    fun onAddToCartClick(product: Product)
}