package com.mahmoudalyudeen.plutus.db.entities

import com.google.common.truth.Truth.assertThat
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import org.junit.Test

class DatabaseBitcoinValueTest {

    private val databaseBitcoinValue = DatabaseBitcoinValue(
        timestamp = 1,
        value = 1.0
    )

    @Test
    fun databaseBitcoinValueAsDomainBitcoinValue() {
        /** GIVEN - a [DatabaseBitcoinValue] */
        val databaseBitcoinValue = databaseBitcoinValue

        /** WHEN - it's converted into a domain [BitcoinValue] */
        val domainBitcoinValue = databaseBitcoinValue.asBitcoinValue()

        /** THEN - both entities should be contain the same data */
        assertThat(databaseBitcoinValue.timestamp).isEqualTo(domainBitcoinValue.timestamp)
        assertThat(databaseBitcoinValue.value).isEqualTo(domainBitcoinValue.value)
    }

    @Test
    fun databaseBitcoinValuesAsDomainBitcoinValues() {
        /** GIVEN - a list of [DatabaseBitcoinValue] */
        val databaseBitcoinValues = listOf(databaseBitcoinValue)

        /** WHEN - it's converted into a list of domain [BitcoinValue] */
        val domainBitcoinValues = listOf(databaseBitcoinValue.asBitcoinValue())

        /** THEN - both lists should be contain the same data */
        assertThat(databaseBitcoinValues.first().timestamp).isEqualTo(domainBitcoinValues.first().timestamp)
        assertThat(databaseBitcoinValues.first().value).isEqualTo(domainBitcoinValues.first().value)
    }
}