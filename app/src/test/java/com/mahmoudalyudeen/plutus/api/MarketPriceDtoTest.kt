package com.mahmoudalyudeen.plutus.api

import com.google.common.truth.Truth
import com.mahmoudalyudeen.plutus.db.entities.DatabaseBitcoinValue
import org.junit.Test

class MarketPriceDtoTest {

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

    @Test
    fun marketPriceDtoAsDatabaseBitcoinValues() {
        /** GIVEN - a [MarketPriceDto] */
        val marketPriceDto = marketPriceDto

        /** WHEN - it's converted into a list of [DatabaseBitcoinValue] */
        val values = marketPriceDto.asDatabaseBitcoinValues()

        /** THEN - both entities should be contain the same data */
        Truth.assertThat(bitcoinValueDto.x).isEqualTo(values.first().timestamp)
        Truth.assertThat(bitcoinValueDto.y).isEqualTo(values.first().value)
    }

}