package com.example.domain.repository

import com.example.domain.model.CartItem
import com.example.domain.model.Order
import com.example.domain.network.ResultWrapper

interface CartRepository {
    suspend fun addToCart(item: CartItem): ResultWrapper<Unit>
    suspend fun getCartItems(userId: String): ResultWrapper<List<CartItem>>
    suspend fun removeFromCart(itemId: String): ResultWrapper<Unit>
    suspend fun createOrder(order: Order): ResultWrapper<Unit>
    suspend fun clearCart(userId: String): ResultWrapper<Unit>
}