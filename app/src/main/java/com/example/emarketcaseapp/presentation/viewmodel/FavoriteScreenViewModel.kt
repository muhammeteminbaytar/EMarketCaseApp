package com.example.emarketcaseapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarketcaseapp.data.repository.FavoriteRepository
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.domain.use_case.get_produckts.GetProductsUseCase
import com.example.emarketcaseapp.domain.use_case.toggle_favorite.ToggleFavoriteUseCase
import com.example.emarketcaseapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    ) : ViewModel() {

    private var job: Job? = null

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    private val _favoriteProductIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteProductIds: StateFlow<List<String>> get() = _favoriteProductIds.asStateFlow()

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts: StateFlow<List<Product>> get() = _favoriteProducts.asStateFlow()


    init {
        loadFavoriteProductIds()
        getProducts()
    }

    fun getProducts() {
        job?.cancel()
        _isLoading.value = true

        job = getProductsUseCase.executeGetProducts().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _products.value = result.data ?: emptyList()
                    updateFavoriteProducts()
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

    private fun loadFavoriteProductIds() {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteIds()
                .collect { ids ->
                    _favoriteProductIds.value = ids
                    updateFavoriteProducts()
                }
        }
    }


    private fun updateFavoriteProducts() {
        val currentProducts = _products.value
        val currentFavoriteIds = _favoriteProductIds.value

        val favoriteList = currentProducts.filter { product ->
            currentFavoriteIds.contains(product.id)
        }

        _favoriteProducts.value = favoriteList
    }
    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(productId)
            loadFavoriteProductIds()
        }
    }
}