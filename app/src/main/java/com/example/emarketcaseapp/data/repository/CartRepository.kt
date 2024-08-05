package com.example.emarketcaseapp.data.repository

import com.example.emarketcaseapp.data.local.CartProduct
import com.example.emarketcaseapp.data.local.CartProductDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartProductDao: CartProductDao
) {
    suspend fun updateCartProduct(productId: String, increase: Boolean) {
        val existingProduct = cartProductDao.getCartProductById(productId)
        if (existingProduct != null) {
            val newQuantity = if (increase) {
                existingProduct.quantity + 1
            } else {
                existingProduct.quantity - 1
            }

            if (newQuantity > 0) {
                cartProductDao.updateCartProduct(existingProduct.copy(quantity = newQuantity))
            } else {
                cartProductDao.deleteCartProduct(existingProduct)
            }
        } else if (increase) {
            cartProductDao.insertCartProduct(CartProduct(productId, 1))
        }
    }

    suspend fun getCartProductById(productId: String): CartProduct? {
        return cartProductDao.getCartProductById(productId)
    }

    suspend fun getAllCartProducts(): List<CartProduct> {
        return cartProductDao.getAllCartProducts()
    }

    fun getAllPiecesCount(): Flow<Int>{
        return cartProductDao.getTotalItemCountFlow()
    }
}