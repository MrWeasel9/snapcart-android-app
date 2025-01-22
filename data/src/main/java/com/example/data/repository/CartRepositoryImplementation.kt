package com.example.data.repository

import com.example.domain.model.CartItem
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CartRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CartRepositoryImplementation : CartRepository {
    private val db = FirebaseFirestore.getInstance()
    private val cartCollection = db.collection("cart")

    override suspend fun addToCart(item: CartItem): ResultWrapper<Unit> {
        return try {
            cartCollection.document(UUID.randomUUID().toString()).set(item).await()
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

    override suspend fun getCartItems(userId: String): ResultWrapper<List<CartItem>> {
        return try {
            val result = cartCollection.whereEqualTo("userId", userId).get().await()
            val cartItems = result.documents.mapNotNull { document ->
                document.toObject(CartItem::class.java)?.copy(id = document.id)
            }
            ResultWrapper.Success(cartItems)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Failure(e)
        }
    }

    override suspend fun removeFromCart(itemId: String): ResultWrapper<Unit> {
        return try {
            cartCollection.document(itemId).delete().await()
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}