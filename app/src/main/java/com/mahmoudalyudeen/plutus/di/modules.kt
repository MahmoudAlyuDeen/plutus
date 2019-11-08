package com.mahmoudalyudeen.plutus.di

import androidx.room.Room
import com.mahmoudalyudeen.plutus.api.ApiServices
import com.mahmoudalyudeen.plutus.db.BitcoinDatabase
import com.mahmoudalyudeen.plutus.repo.BitcoinRepository
import com.mahmoudalyudeen.plutus.ui.bitcoin.BitcoinViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    single { ApiServices().createBitcoinApi() }

    single {
        Room.databaseBuilder(get(), BitcoinDatabase::class.java, "bitcoindatabase").build()
    }
}

val bitcoinModule = module {

    single { BitcoinRepository(bitcoinDatabase = get(), bitcoinApi = get()) }

    viewModel { BitcoinViewModel(bitcoinRepository = get()) }
}
