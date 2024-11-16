package com.example.domain.usecase

import com.example.domain.repository.ProductRepository

class GetProductUseCase (private val repository: ProductRepository){
    suspend fun execute() = repository.getProducts()
}