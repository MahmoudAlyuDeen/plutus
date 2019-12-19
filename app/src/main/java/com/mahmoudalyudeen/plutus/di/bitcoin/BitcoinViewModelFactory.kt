package com.mahmoudalyudeen.plutus.di.bitcoin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudalyudeen.plutus.repo.BitcoinRepository
import com.mahmoudalyudeen.plutus.ui.bitcoin.BitcoinViewModel
import javax.inject.Inject

class BitcoinViewModelFactory @Inject constructor(private val bitcoinRepository: BitcoinRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(BitcoinViewModel::class.java) -> BitcoinViewModel(bitcoinRepository)
        else -> throw Exception()
    } as T
}