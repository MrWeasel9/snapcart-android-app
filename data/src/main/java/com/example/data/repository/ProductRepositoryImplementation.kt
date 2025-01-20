package com.example.data.repository

import com.example.domain.model.Product
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.ProductRepository

class ProductRepositoryImplementation(private val networkService: NetworkService) : ProductRepository {
    override suspend fun getProducts(category: String?) : ResultWrapper<List<Product>> {
        return networkService.getProducts(category)
    }

    // Add implementation
    override suspend fun getProductById(id: Long): ResultWrapper<Product> {
        return networkService.getProductById(id)
    }

}