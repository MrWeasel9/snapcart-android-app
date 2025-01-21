package com.example.data.di

import com.example.data.repository.CartRepositoryImplementation
import com.example.domain.repository.CartRepository
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, repositoryModule)
    single<CartRepository> { CartRepositoryImplementation() }
}