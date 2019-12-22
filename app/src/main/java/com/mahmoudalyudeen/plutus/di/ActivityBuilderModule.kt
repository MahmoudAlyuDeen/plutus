package com.mahmoudalyudeen.plutus.di

import com.mahmoudalyudeen.plutus.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

}