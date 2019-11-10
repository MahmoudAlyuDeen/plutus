package com.mahmoudalyudeen.plutus.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.mahmoudalyudeen.plutus.api.ApiServices
import com.mahmoudalyudeen.plutus.api.MarketPriceDto
import com.mahmoudalyudeen.plutus.db.BitcoinDatabase
import com.mahmoudalyudeen.plutus.db.entities.DatabaseBitcoinValue
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import com.mahmoudalyudeen.plutus.util.valueSync
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BitcoinRepositoryTest {

    @MockK
    private lateinit var bitcoinApi: ApiServices.BitcoinApi

    @MockK
    private lateinit var bitcoinDatabase: BitcoinDatabase

    private lateinit var bitcoinRepository: BitcoinRepository

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val bitcoinValueDto = MarketPriceDto.ValueDto(
        x = 1,
        y = 1.0
    )

    private val marketPriceDto = MarketPriceDto(
        description = "description",
        name = "name",
        period = "period",
        status = "status",
        unit = "unit",
        values = listOf(bitcoinValueDto)
    )

    private val databaseBitcoinValue = DatabaseBitcoinValue(
        timestamp = 1,
        value = 1.0
    )

    private val domainBitcoinValue = BitcoinValue(
        timestamp = 1,
        value = 1.0
    )

    @Before
    fun setupTest() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun loadBitcoinValuesFromDatabase() {
        /** GIVEN - a [BitcoinDatabase] that contains a [DatabaseBitcoinValue] */
        val values = MutableLiveData(listOf(databaseBitcoinValue))
        every { bitcoinDatabase.bitcoinValueDao.getBitcoinValues() } returns values

        /** WHEN - [BitcoinRepository] is instantiated */
        bitcoinRepository = BitcoinRepository(bitcoinDatabase, bitcoinApi)

        /** THEN - [BitcoinRepository.bitcoinValues] contains a list of [BitcoinValue] */
        assertThat(bitcoinRepository.bitcoinValues.valueSync).isEqualTo(listOf(domainBitcoinValue))
    }

    @Test
    fun fetchBitcoinValuesAndSaveToDatabase() = runBlocking {
        /** GIVEN - [ApiServices.BitcoinApi] returns a [MarketPriceDto] containing a list of [MarketPriceDto.ValueDto] */
        coEvery { bitcoinApi.getMarketPrice() } returns marketPriceDto

        /** WHEN - [BitcoinRepository.fetchBitcoinValues] is called */
        bitcoinRepository = BitcoinRepository(bitcoinDatabase, bitcoinApi)
        bitcoinRepository.fetchBitcoinValues()

        /** THEN - [BitcoinDatabase.bitcoinValueDao] is called to persist a mapped list of [DatabaseBitcoinValue] */
        coVerify { bitcoinDatabase.bitcoinValueDao.insertBitcoinValues(listOf(databaseBitcoinValue)) }
    }
}