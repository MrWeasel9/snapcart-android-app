package com.example.domain.model

// domain/src/main/java/com/example/domain/model/CartItem.kt
data class CartItem(
    val id: String = "",
    val userId: String = "",
    val productId: Long = 0L,
    val productName: String = "",
    val addedAt: Long = 0L
) {
    // Required empty constructor for Firestore
    constructor() : this("", "", 0L, "", 0L)
}