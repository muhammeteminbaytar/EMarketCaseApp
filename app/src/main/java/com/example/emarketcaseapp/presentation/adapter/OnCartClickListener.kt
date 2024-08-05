package com.example.emarketcaseapp.presentation.adapter

import com.example.emarketcaseapp.domain.model.CartModel
import com.example.emarketcaseapp.domain.model.Product

interface OnCartClickListener {
    fun onMinusClick(cart: CartModel)
    fun onPlusClick(cart: CartModel)
}