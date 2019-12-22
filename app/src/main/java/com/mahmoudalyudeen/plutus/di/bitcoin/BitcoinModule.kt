package com.mahmoudalyudeen.plutus.di.bitcoin

import androidx.lifecycle.ViewModelProviders
import com.mahmoudalyudeen.plutus.api.ApiServices
import com.mahmoudalyudeen.plutus.db.BitcoinDatabase
import com.mahmoudalyudeen.plutus.db.BitcoinValueDao
import com.mahmoudalyudeen.plutus.repo.BitcoinRepository
import com.mahmoudalyudeen.plutus.ui.bitcoin.BitcoinFragment
import com.mahmoudalyudeen.plutus.ui.bitcoin.BitcoinViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object BitcoinModule {

    @JvmStatic
    @Provides
    fun provideBitcoinApi(retrofit: Retrofit): ApiServices.BitcoinApi =
        retrofit.create(ApiServices.BitcoinApi::class.java)

    @JvmStatic
    @Provides
    fun provideBitcoinValueDao(bitcoinDatabase: BitcoinDatabase): BitcoinValueDao = bitcoinDatabase.bitcoinValueDao

    @JvmStatic
    @Provides
    fun providesRepository(bitcoinApi: ApiServices.BitcoinApi, bitcoinValueDao: BitcoinValueDao) =
        BitcoinRepository(bitcoinApi, bitcoinValueDao)

    @JvmStatic
    @Provides
    fun providesViewModel(fragment: BitcoinFragment, factory: BitcoinViewModelFactory): BitcoinViewModel =
        ViewModelProviders.of(fragment, factory).get(BitcoinViewModel::class.java)
}