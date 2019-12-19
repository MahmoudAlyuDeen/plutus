package com.mahmoudalyudeen.plutus

import com.mahmoudalyudeen.plutus.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

@Suppress("unused") // used in manifest
open class App : DaggerApplication() {

    private val injector by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = injector
}