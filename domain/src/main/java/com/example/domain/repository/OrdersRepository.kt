package com.example.domain.repository

import com.example.domain.model.Order
import com.example.domain.network.ResultWrapper

interface OrdersRepository {
    suspend fun getOrders(userId: String): ResultWrapper<List<Order>>
}