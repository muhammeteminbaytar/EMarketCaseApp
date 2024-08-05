package com.example.emarketcaseapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _menuVisibility = MutableStateFlow(MenuVisibility(false, false))
    val menuVisibility: StateFlow<MenuVisibility> = _menuVisibility

    private val _isBackButtonVisible = MutableStateFlow(false)
    val isBackButtonVisible: StateFlow<Boolean> = _isBackButtonVisible

    private val _favoriteCount = MutableStateFlow(0)
    val favoriteCount: StateFlow<Int> = _favoriteCount

    private val visibleMenuFragmentId = R.id.homeScreen
    private val backButtonFragmentId = R.id.detailScreenFragment

    init {
        getFavoriteCount()
    }

    fun updateMenuVisibility(destinationId: Int) {
        viewModelScope.launch {
            _menuVisibility.value = if (destinationId == visibleMenuFragmentId) {
                MenuVisibility(true, true)
            } else {
                MenuVisibility(false, false)
            }
            _isBackButtonVisible.value = destinationId == backButtonFragmentId
        }
    }

    private fun getFavoriteCount() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteCount().collect {
                _favoriteCount.value = it
            }
        }
    }
}

data class MenuVisibility(val isSearchVisible: Boolean, val isFilterVisible: Boolean)
