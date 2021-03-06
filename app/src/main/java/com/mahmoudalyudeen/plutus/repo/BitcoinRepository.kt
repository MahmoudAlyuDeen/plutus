package com.mahmoudalyudeen.plutus.repo

import androidx.lifecycle.Transformations.map
import com.mahmoudalyudeen.plutus.api.BitcoinApi
import com.mahmoudalyudeen.plutus.api.asDatabaseBitcoinValues
import com.mahmoudalyudeen.plutus.db.BitcoinDatabase
import com.mahmoudalyudeen.plutus.db.BitcoinValueDao
import com.mahmoudalyudeen.plutus.db.entities.asDomainBitcoinValues
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BitcoinRepository(private val bitcoinApi: BitcoinApi, private val bitcoinValueDao: BitcoinValueDao) {

    /** Bitcoin values from [BitcoinDatabase] presented as [BitcoinValue] ready for offline consumption */
    val bitcoinValues =
        map(bitcoinValueDao.getBitcoinValues()) { it.asDomainBitcoinValues() }

    /** Fetches bitcoin values from [BitcoinApi] and saves them to [BitcoinDatabase] */
    suspend fun fetchBitcoinValues() {
        withContext(Dispatchers.IO) {
            val marketPriceDto = bitcoinApi.getMarketPrice()
            bitcoinValueDao.insertBitcoinValues(marketPriceDto.asDatabaseBitcoinValues())
        }
    }
}
