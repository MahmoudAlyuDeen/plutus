package com.mahmoudalyudeen.plutus

import android.app.Application
import com.mahmoudalyudeen.plutus.di.bitcoinModule
import com.mahmoudalyudeen.plutus.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused") // used in manifest
open class App : Application() {

    override fun onCreate() {
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, bitcoinModule))
        }
        super.onCreate()
    }
}