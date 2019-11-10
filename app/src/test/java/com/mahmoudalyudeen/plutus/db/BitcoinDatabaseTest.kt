package com.mahmoudalyudeen.plutus.db

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.mahmoudalyudeen.plutus.db.entities.DatabaseBitcoinValue
import com.mahmoudalyudeen.plutus.util.valueSync
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class BitcoinDatabaseTest {

    /** Executing each task synchronously using Architecture Components */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: BitcoinDatabase

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BitcoinDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    private val databaseBitcoinValue = DatabaseBitcoinValue(
        timestamp = 1,
        value = 1.0
    )

    private val databaseBitcoinValue2 = DatabaseBitcoinValue(
        timestamp = 1,
        value = 2.0
    )

    @Test
    fun insertAndLoadBitcoinValue() = runBlockingTest {
        /** GIVEN - a [DatabaseBitcoinValue] */
        val databaseBitcoinValue = databaseBitcoinValue

        /** WHEN - it's inserted into then loaded from [BitcoinDatabase] */
        database.bitcoinValueDao.insertBitcoinValues(listOf(databaseBitcoinValue))
        val loaded = database.bitcoinValueDao.getBitcoinValues()

        /** THEN - both entities should be contain the same data */
        assertThat(databaseBitcoinValue.value).isEqualTo(loaded.valueSync.first().value)
        assertThat(databaseBitcoinValue.timestamp).isEqualTo(loaded.valueSync.first().timestamp)
    }

    @Test
    fun insertBitcoinValueReplaceOnConflict() = runBlockingTest {
        /** GIVEN - two [DatabaseBitcoinValue] objects with the same [DatabaseBitcoinValue.timestamp] */
        val databaseBitcoinValue = databaseBitcoinValue
        val databaseBitcoinValue2 = databaseBitcoinValue2

        /** WHEN - both objects are inserted into [BitcoinDatabase] one after another */
        database.bitcoinValueDao.insertBitcoinValues(listOf(databaseBitcoinValue))
        database.bitcoinValueDao.insertBitcoinValues(listOf(databaseBitcoinValue2))

        val loaded = database.bitcoinValueDao.getBitcoinValues()

        /** THEN - [BitcoinDatabase] will only contain [databaseBitcoinValue2] */
        assertThat(loaded.valueSync.size).isEqualTo(1)
        assertThat(loaded.valueSync.first().value).isEqualTo(databaseBitcoinValue2.value)
    }
}