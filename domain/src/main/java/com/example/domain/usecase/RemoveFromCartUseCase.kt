package com.example.domain.usecase

import com.example.domain.repository.CartRepository

class RemoveFromCartUseCase(private val repository: CartRepository) {
    suspend fun execute(itemId: String) = repository.removeFromCart(itemId)
}