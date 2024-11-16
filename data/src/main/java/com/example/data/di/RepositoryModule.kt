package com.example.data.di

import com.example.data.repository.ProductRepositoryImplementation
import com.example.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImplementation(get()) }
}