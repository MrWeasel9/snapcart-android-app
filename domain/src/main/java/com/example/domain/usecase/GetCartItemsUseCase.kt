package com.example.domain.usecase

import com.example.domain.repository.CartRepository

// GetCartItemsUseCase.kt
class GetCartItemsUseCase(private val repository: CartRepository) {
    suspend fun execute(userId: String) = repository.getCartItems(userId)
}