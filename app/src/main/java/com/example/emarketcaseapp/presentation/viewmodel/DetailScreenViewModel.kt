package com.example.emarketcaseapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarketcaseapp.data.repository.FavoriteRepository
import com.example.emarketcaseapp.domain.use_case.toggle_favorite.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val favoriteRepository: FavoriteRepository
    ) : ViewModel() {

    fun isFavorite(productId: String): Flow<Boolean> {
        return favoriteRepository.isFavorite(productId)
    }
    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(productId)
        }
    }
}