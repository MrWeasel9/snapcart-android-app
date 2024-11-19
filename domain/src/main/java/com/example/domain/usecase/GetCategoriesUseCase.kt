package com.example.domain.usecase

import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend fun execute(): ResultWrapper<List<String>> {
        return repository.getCategories()
    }
}