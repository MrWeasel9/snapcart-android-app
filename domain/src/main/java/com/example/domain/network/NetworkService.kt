package com.example.domain.network

import com.example.domain.model.Product

interface NetworkService {
    suspend fun getProducts(category:String?): ResultWrapper<List<Product>>
    suspend fun getCategories(): ResultWrapper<List<String>>
    // Add to interface
    suspend fun getProductById(id: Long): ResultWrapper<Product>
}
// This wrapper allows consistent handling of success and failure scenarios.
sealed class  ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}