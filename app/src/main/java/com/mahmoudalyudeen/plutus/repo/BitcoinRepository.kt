package com.mahmoudalyudeen.plutus.repo

import androidx.lifecycle.Transformations.map
import com.mahmoudalyudeen.plutus.api.ApiServices
import com.mahmoudalyudeen.plutus.api.asDatabaseBitcoinValues
import com.mahmoudalyudeen.plutus.db.BitcoinDatabase
import com.mahmoudalyudeen.plutus.db.entities.asDomainBitcoinValues
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BitcoinRepository(private val bitcoinDatabase: BitcoinDatabase, private val bitcoinApi: ApiServices.BitcoinApi) {

    /** Bitcoin values from [BitcoinDatabase] presented as [BitcoinValue] ready for offline consumption */
    val bitcoinValues = map(bitcoinDatabase.bitcoinValueDao.getBitcoinValues()) { it.asDomainBitcoinValues() }

    /** Fetches bitcoin values from [ApiServices.BitcoinApi] and saves them to [BitcoinDatabase] */
    suspend fun fetchBitcoinValues() {
        withContext(Dispatchers.IO) {
            val marketPriceDto = bitcoinApi.getMarketPrice()
            bitcoinDatabase.bitcoinValueDao.insertBitcoinValues(marketPriceDto.asDatabaseBitcoinValues())
        }
    }
}
