package com.example.data.di

import com.example.data.repository.CategoryRepositoryImplementation
import com.example.data.repository.ProductRepositoryImplementation
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.ProductRepository
import org.koin.dsl.module

    // Defines a unique instance of ProductRepository and CategoryRepository
val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImplementation(get()) }
    single<CategoryRepository> { CategoryRepositoryImplementation(get()) }
}