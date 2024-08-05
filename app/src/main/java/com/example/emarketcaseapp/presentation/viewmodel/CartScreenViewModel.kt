package com.example.emarketcaseapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarketcaseapp.data.local.CartProduct
import com.example.emarketcaseapp.data.repository.CartRepository
import com.example.emarketcaseapp.domain.model.CartModel
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.domain.use_case.get_produckts.GetProductsUseCase
import com.example.emarketcaseapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val cartRepository: CartRepository

    ) : ViewModel() {
    private var job: Job? = null
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _cartProducts = MutableStateFlow<List<CartProduct>>(emptyList())
    val cartProducts: StateFlow<List<CartProduct>> get() = _cartProducts

    private val _filteredProducts = MutableStateFlow<List<CartModel>>(emptyList())
    val filteredProducts: StateFlow<List<CartModel>> get() = _filteredProducts

    init {
        loadCartProducts()
        observeCartProducts()
    }


    fun loadCartProducts() {
        viewModelScope.launch {
            _cartProducts.value = cartRepository.getAllCartProducts()
        }
    }

    fun observeCartProducts() {
        cartProducts.combine(products) { cartProducts, allProducts ->
            cartProducts.mapNotNull { cartProduct ->
                val product = allProducts.find { it.id == cartProduct.productId }
                product?.let {
                    CartModel(product = it, piece = cartProduct.quantity.toString())
                }
            }
        }.onEach { cartModelList ->
            _filteredProducts.value = cartModelList
        }.launchIn(viewModelScope)
    }

    fun updateCartProduct(productId: String, increase: Boolean) {
        viewModelScope.launch {
            cartRepository.updateCartProduct(productId, increase)
            loadCartProducts()
        }
    }


    fun getProducts() {
        job?.cancel()
        _isLoading.value = true

        job = getProductsUseCase.executeGetProducts().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _products.value = result.data ?: emptyList()
                }

                is Resource.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.message
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }



}