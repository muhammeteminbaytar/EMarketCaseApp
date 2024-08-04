package com.example.emarketcaseapp.domain.use_case.toggle_favorite

import com.example.emarketcaseapp.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(productId: String) {
        val isFavorite = favoriteRepository.isFavorite(productId).first()
        if (isFavorite) {
            favoriteRepository.removeFavorite(productId)
        } else {
            favoriteRepository.addFavorite(productId)
        }
    }
}