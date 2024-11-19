package com.example.domain.di

import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module{
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
}