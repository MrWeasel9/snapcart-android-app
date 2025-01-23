package com.example.domain.di

import com.example.domain.usecase.AddToCartUseCase
import com.example.domain.usecase.ClearCartUseCase
import com.example.domain.usecase.CreateOrderUseCase
import com.example.domain.usecase.GetCartItemsUseCase
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetOrdersUseCase
import com.example.domain.usecase.GetProductByIdUseCase
import com.example.domain.usecase.GetProductUseCase
import com.example.domain.usecase.RemoveFromCartUseCase
import org.koin.dsl.module

val useCaseModule = module{
    // A new instance will be created each time it's needed, and its dependency is resolved using `get()`.
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { GetProductByIdUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { GetCartItemsUseCase(get()) }
    factory { RemoveFromCartUseCase(get()) }
    factory { CreateOrderUseCase(get()) }
    factory { ClearCartUseCase(get()) }
    factory { GetOrdersUseCase(get()) }
}