package com.example.snapcart_android_app

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.snapcart_android_app.di.AppModules.appModule  // Updated import
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopperApp)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}