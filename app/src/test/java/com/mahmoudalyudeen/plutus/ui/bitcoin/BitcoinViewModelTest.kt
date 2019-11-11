package com.mahmoudalyudeen.plutus.ui.bitcoin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.mahmoudalyudeen.plutus.MainCoroutineRule
import com.mahmoudalyudeen.plutus.api.CallStatus
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import com.mahmoudalyudeen.plutus.repo.BitcoinRepository
import com.mahmoudalyudeen.plutus.util.valueSync
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BitcoinViewModelTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var bitcoinRepository: BitcoinRepository

    private lateinit var bitcoinViewModel: BitcoinViewModel

    private val bitcoinValue = BitcoinValue(
        timestamp = 1,
        value = 1.0
    )

    private val bitcoinValues = MutableLiveData(listOf(bitcoinValue))

    @Before
    fun setupViewModel() {
        MockKAnnotations.init(this, relaxed = true)

        coEvery { bitcoinRepository.fetchBitcoinValues() } just runs
    }

    @Test
    fun initNoCacheShouldShowLoading() {
        /** GIVEN - [BitcoinRepository.bitcoinValues] is empty */
        val emptyBitcoinValues = MutableLiveData<List<BitcoinValue>>()
        every { bitcoinRepository.bitcoinValues } returns emptyBitcoinValues

        /** WHEN - [BitcoinViewModel] is instantiated, pausing dispatcher to capture the state before the coroutine finishes */
        mainCoroutineRule.pauseDispatcher()
        bitcoinViewModel = BitcoinViewModel(bitcoinRepository)

        /** THEN - [BitcoinViewModel.callStatus] = [CallStatus.Loading] to signal the view to show loading */
        assertThat(bitcoinViewModel.callStatus.valueSync).isEqualTo(CallStatus.Loading)
    }

    @Test
    fun initCacheShouldShowContent() {
        /** GIVEN - [BitcoinRepository.bitcoinValues] contains a [BitcoinValue] */
        every { bitcoinRepository.bitcoinValues } returns bitcoinValues

        /** WHEN - [BitcoinViewModel] is instantiated, pausing dispatcher to capture the state before the coroutine finishes */
        mainCoroutineRule.pauseDispatcher()
        bitcoinViewModel = BitcoinViewModel(bitcoinRepository)

        /** THEN - [BitcoinViewModel.callStatus] = [CallStatus.Idle] to signal the view to show content */
        assertThat(bitcoinViewModel.callStatus.valueSync).isEqualTo(CallStatus.Idle)
        assertThat(bitcoinViewModel.bitcoinValues.valueSync).isNotEmpty()
    }

    @Test
    fun initFetchSuccessShowLoadingThenContent() {
        /** GIVEN - [BitcoinRepository.bitcoinValues] is initially empty */
        val emptyBitcoinValues = MutableLiveData<List<BitcoinValue>>()
        every { bitcoinRepository.bitcoinValues } returns emptyBitcoinValues

        /** WHEN - [BitcoinViewModel] is instantiated, pausing dispatcher to capture the state before the coroutine finishes */
        mainCoroutineRule.pauseDispatcher()
        bitcoinViewModel = BitcoinViewModel(bitcoinRepository)

        /** THEN - [BitcoinViewModel.callStatus] = [CallStatus.Loading] to signal the view to show loading */
        assertThat(bitcoinViewModel.callStatus.valueSync).isEqualTo(CallStatus.Loading)

        /** WHEN - the coroutine finishes and [BitcoinRepository.bitcoinValues] now contains a [BitcoinValue] */
        every { bitcoinRepository.bitcoinValues } returns bitcoinValues
        mainCoroutineRule.resumeDispatcher()

        /** THEN - [BitcoinViewModel.callStatus] = [CallStatus.Idle] to signal the view to show content */
        assertThat(bitcoinViewModel.callStatus.valueSync).isEqualTo(CallStatus.Idle)
    }

    @Test
    fun initFetchFailureShowLoadingThenError() {
        /** GIVEN - [BitcoinRepository.bitcoinValues] is initially empty */
        val emptyBitcoinValues = MutableLiveData<List<BitcoinValue>>()
        every { bitcoinRepository.bitcoinValues } returns emptyBitcoinValues

        /** WHEN - [BitcoinViewModel] is instantiated, pausing dispatcher to capture the state before the coroutine finishes */
        mainCoroutineRule.pauseDispatcher()
        bitcoinViewModel = BitcoinViewModel(bitcoinRepository)

        /** THEN - [BitcoinViewModel.callStatus] = [CallStatus.Loading] to signal the view to show loading */
        assertThat(bitcoinViewModel.callStatus.valueSync).isEqualTo(CallStatus.Loading)

        /** WHEN - the coroutine throws an exception before finishing */
        coEvery { bitcoinRepository.fetchBitcoinValues() } throws Exception()
        mainCoroutineRule.resumeDispatcher()

        /** THEN - [BitcoinViewModel.callStatus] = [CallStatus.Error] to signal the view to show error */
        assertThat(bitcoinViewModel.callStatus.valueSync).isEqualTo(CallStatus.Error)
    }
}