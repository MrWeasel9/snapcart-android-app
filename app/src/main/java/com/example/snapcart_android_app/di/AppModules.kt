package com.example.snapcart_android_app.di

import com.example.snapcart_android_app.ui.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


object AppModules {
    // Defines a unique instance of HomeViewModel
    val viewModelModule = module {
        viewModel {
            HomeViewModel(get(), get())
        }
    }

    // Defines a unique instance of AppModule
    val appModule = module {
        includes(
            viewModelModule
        )
    }
}
