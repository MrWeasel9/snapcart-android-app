package com.example.snapcart_android_app.di

import com.example.snapcart_android_app.ui.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Combined modules into a single file
object AppModules {
    val viewModelModule = module {
        viewModel {
            HomeViewModel(get())
        }
    }

    val appModule = module {
        includes(
            viewModelModule
        )
    }
}
