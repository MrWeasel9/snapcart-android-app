package com.example.snapcart_android_app.di

import com.example.snapcart_android_app.ui.CartViewModel
import com.example.snapcart_android_app.ui.OrdersViewModel
import com.example.snapcart_android_app.ui.UserViewModel
import com.example.snapcart_android_app.ui.feature.home.HomeViewModel
import com.example.snapcart_android_app.ui.feature.detail.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


object AppModules {
    // Defines a unique instance of HomeViewModel
    val viewModelModule = module {
        viewModel {
            HomeViewModel(get(), get())
        }
        viewModel { ProductDetailViewModel(get()) }
        viewModel { CartViewModel(get(), get(), get(), get(), get(), get()) }
        viewModel { UserViewModel() }
        viewModel { OrdersViewModel(get()) }
        // Add ViewModel to Koin modules

    }

    // Defines a unique instance of AppModule
    val appModule = module {

        includes(
            viewModelModule
        )
    }
}
