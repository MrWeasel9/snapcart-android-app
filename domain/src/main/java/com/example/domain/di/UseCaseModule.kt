package com.example.domain.di

import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductByIdUseCase
import com.example.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module{
    // A new instance will be created each time it's needed, and its dependency is resolved using `get()`.
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { GetProductByIdUseCase(get()) }
}