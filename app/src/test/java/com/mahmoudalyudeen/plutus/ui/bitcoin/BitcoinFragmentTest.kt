package com.mahmoudalyudeen.plutus.ui.bitcoin

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.mahmoudalyudeen.plutus.R
import com.mahmoudalyudeen.plutus.api.CallStatus
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

@RunWith(RobolectricTestRunner::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@Config(sdk = [Build.VERSION_CODES.P])
class BitcoinFragmentTest : AutoCloseKoinTest() {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var bitcoinViewModel: BitcoinViewModel

    private val idleStatus = MutableLiveData(CallStatus.Idle)
    private val loadingStatus = MutableLiveData(CallStatus.Loading)
    private val errorStatus = MutableLiveData(CallStatus.Error)

    private val bitcoinValue = BitcoinValue(
        timestamp = 1,
        value = 1.0
    )

    private val emptyBitcoinValues = MutableLiveData<List<BitcoinValue>>()
    private val bitcoinValues = MutableLiveData(listOf(bitcoinValue))

    private fun launchFragment() = launchFragmentInContainer<BitcoinFragment>(null, R.style.AppTheme)

    @Before
    fun setupFragment() {
        MockKAnnotations.init(this, relaxed = true)

        val mockModule = module {
            viewModel { bitcoinViewModel }
        }

        /** Injecting a mocked [BitcoinViewModel] */
        startKoin {
            modules(mockModule)
        }
    }

    @Test
    fun initNoCacheShouldShowLoading() {
        /** GIVEN - [BitcoinViewModel.callStatus] = [CallStatus.Loading] */
        every { bitcoinViewModel.callStatus } returns loadingStatus
        every { bitcoinViewModel.bitcoinValues } returns emptyBitcoinValues

        /** WHEN - [BitcoinFragment] is launched */
        launchFragment()

        /** THEN - only loading view is displayed */
        onView(ViewMatchers.withId(R.id.bitcoinLoading)).check(matches(ViewMatchers.isCompletelyDisplayed()))
        onView(ViewMatchers.withId(R.id.bitcoinChart)).check(matches(not(ViewMatchers.isDisplayed())))
        onView(ViewMatchers.withId(R.id.bitcoinError)).check(matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun initCacheShouldShowContent() {
        /** GIVEN - [BitcoinViewModel.callStatus] = [CallStatus.Idle] */
        every { bitcoinViewModel.callStatus } returns idleStatus
        every { bitcoinViewModel.bitcoinValues } returns bitcoinValues

        /** WHEN - [BitcoinFragment] is launched */
        launchFragment()

        /** THEN - only content view is displayed */
        onView(ViewMatchers.withId(R.id.bitcoinChart)).check(matches(ViewMatchers.isCompletelyDisplayed()))
        onView(ViewMatchers.withId(R.id.bitcoinLoading)).check(matches(not(ViewMatchers.isDisplayed())))
        onView(ViewMatchers.withId(R.id.bitcoinError)).check(matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun initFetchFailureShowError() {
        /** GIVEN - [BitcoinViewModel.callStatus] = [CallStatus.Error] */
        every { bitcoinViewModel.callStatus } returns errorStatus
        every { bitcoinViewModel.bitcoinValues } returns bitcoinValues

        /** WHEN - [BitcoinFragment] is launched */
        launchFragment()

        /** THEN - only content view is displayed */
        onView(ViewMatchers.withId(R.id.bitcoinError)).check(matches(ViewMatchers.isCompletelyDisplayed()))
        onView(ViewMatchers.withId(R.id.bitcoinChart)).check(matches(not(ViewMatchers.isDisplayed())))
        onView(ViewMatchers.withId(R.id.bitcoinLoading)).check(matches(not(ViewMatchers.isDisplayed())))
    }
}