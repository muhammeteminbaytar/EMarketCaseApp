package com.example.emarketcaseapp.presentation.viewmodel

import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.domain.use_case.get_produckts.GetProductsUseCase
import com.example.emarketcaseapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private var job: Job? = null

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults

    private val _selectedSortCriteria = MutableStateFlow<Int?>(null)
    val selectedSortCriteria: StateFlow<Int?> = _selectedSortCriteria

    private var currentSortCriteria: String? = null


    fun getProducts() {
        job?.cancel()
        _isLoading.value = true

        job = getProductsUseCase.executeGetProducts().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _products.value = result.data ?: emptyList()
                    _searchResults.value = result.data ?: emptyList()
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

    fun searchProducts(query: String) {
        val filteredList = _products.value.filter {
            it.name.startsWith(query, ignoreCase = true)
        }
        _searchResults.value = filteredList
    }

    fun onSortCriteriaChanged(checkedId: Int) {
        _selectedSortCriteria.value = checkedId
        currentSortCriteria = when(checkedId) {
            R.id.rb_olt_to_new -> "date_asc"
            R.id.rb_new_to_old -> "date_desc"
            R.id.rb_price_low_to_high -> "price_asc"
            R.id.rb_price_high_to_low -> "price_desc"
            else -> null
        }
    }
    val onCheckedChangeListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        _selectedSortCriteria.value = checkedId
        currentSortCriteria = when (checkedId) {
            R.id.rb_olt_to_new -> "date_asc"
            R.id.rb_new_to_old -> "date_desc"
            R.id.rb_price_low_to_high -> "price_asc"
            R.id.rb_price_high_to_low -> "price_desc"
            else -> null
        }
    }

    fun filterProducts(criteria: String) {
        val sortedList = when(criteria) {
            "date_asc" -> _searchResults.value.sortedBy { it.createdAt }
            "date_desc" -> _searchResults.value.sortedByDescending { it.createdAt }
            "price_asc" -> _searchResults.value.sortedBy { it.price.toDoubleOrNull() }
            "price_desc" -> _searchResults.value.sortedByDescending { it.price.toDoubleOrNull() }
            else -> _searchResults.value
        }
        _searchResults.value = sortedList
    }

    fun applyFilters() {
        currentSortCriteria?.let { filterProducts(it) }
    }
}