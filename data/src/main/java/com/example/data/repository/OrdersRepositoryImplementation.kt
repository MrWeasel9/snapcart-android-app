package com.example.data.repository

import android.util.Log
import com.example.domain.model.CartItem
import com.example.domain.model.Order
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.OrdersRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class OrdersRepositoryImplementation : OrdersRepository {
    private val db = FirebaseFirestore.getInstance()
    private val ordersCollection = db.collection("orders")  // Fixed: Initialize collection

    override suspend fun getOrders(userId: String): ResultWrapper<List<Order>> {
        Log.d("OrdersRepo", "Fetching orders for user: $userId")
        return try {
            val result = ordersCollection
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val orders = result.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                Order(
                    id = doc.id,
                    userId = data["userId"] as String,
                    items = parseCartItems(data["items"] as List<Map<String, Any>>),
                    totalPrice = data["totalPrice"] as Double,
                    orderDate = (data["orderDate"] as Timestamp).toDate()
                )
            }
            Log.d("OrdersRepo", "Found ${orders.size} orders")
            ResultWrapper.Success(orders)
        } catch (e: Exception) {
            Log.e("OrdersRepo", "Error fetching orders: ${e.message}")
            ResultWrapper.Failure(e)
        }
    }

    private fun parseCartItems(items: List<Map<String, Any>>): List<CartItem> {
        return items.map { itemMap ->
            CartItem(
                id = itemMap["id"] as String,
                userId = itemMap["userId"] as String,
                productId = (itemMap["productId"] as Number).toLong(),  // Handle numeric types
                productName = itemMap["productName"] as String,
                addedAt = (itemMap["addedAt"] as Number).toLong()
            )
        }
    }
}