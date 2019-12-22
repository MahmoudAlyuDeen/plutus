package com.mahmoudalyudeen.plutus.di

import com.mahmoudalyudeen.plutus.di.bitcoin.BitcoinModule
import com.mahmoudalyudeen.plutus.ui.bitcoin.BitcoinFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [BitcoinModule::class])
    abstract fun provideBitcoinFragment(): BitcoinFragment
}
