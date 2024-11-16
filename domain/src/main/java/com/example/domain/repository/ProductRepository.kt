package com.example.domain.repository

import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper

interface ProductRepository {
    suspend fun getProducts(): ResultWrapper<List<Product>>
}