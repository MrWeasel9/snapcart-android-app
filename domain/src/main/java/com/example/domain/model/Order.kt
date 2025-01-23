// domain/model/Order.kt
package com.example.domain.model

import java.util.Date

data class Order(
    val id: String = "",
    val userId: String,
    val items: List<CartItem>,
    val totalPrice: Double,
    val orderDate: Date = Date()
)