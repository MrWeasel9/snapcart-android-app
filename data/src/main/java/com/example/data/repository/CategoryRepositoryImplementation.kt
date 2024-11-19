package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CategoryRepository

class CategoryRepositoryImplementation(val networkService: NetworkService) : CategoryRepository {
    override suspend fun getCategories(): ResultWrapper<List<String>> {
        return networkService.getCategories()
    }
}