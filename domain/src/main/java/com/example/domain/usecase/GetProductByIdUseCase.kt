package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.ProductRepository

class GetProductByIdUseCase(private val repository: ProductRepository) {
    suspend fun execute(id: Long): ResultWrapper<Product> {
        return repository.getProductById(id)
    }
}