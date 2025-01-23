package com.example.domain.usecase

import com.example.domain.model.Order
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.OrdersRepository

class GetOrdersUseCase(private val repository: OrdersRepository) {
    suspend fun execute(userId: String): ResultWrapper<List<Order>> {
        return repository.getOrders(userId)
    }
}