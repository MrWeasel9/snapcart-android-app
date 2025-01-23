// domain/usecase/ClearCartUseCase.kt
package com.example.domain.usecase

import com.example.domain.repository.CartRepository

class ClearCartUseCase(private val repository: CartRepository) {
    suspend fun execute(userId: String) = repository.clearCart(userId)
}