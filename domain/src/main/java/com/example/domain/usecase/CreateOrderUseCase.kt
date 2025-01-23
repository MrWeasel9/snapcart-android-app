// domain/usecase/CreateOrderUseCase.kt
package com.example.domain.usecase

import com.example.domain.model.Order
import com.example.domain.repository.CartRepository

class CreateOrderUseCase(private val repository: CartRepository) {
    suspend fun execute(order: Order) = repository.createOrder(order)
}