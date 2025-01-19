package com.example.snapcart_android_app

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.snapcart_android_app.di.AppModules.appModule  // Updated import
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class SnapCartApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SnapCartApp)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule
                )
            )
        }
        // Initialize FirebaseAppCheck
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }
}