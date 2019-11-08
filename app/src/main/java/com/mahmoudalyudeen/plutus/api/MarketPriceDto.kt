package com.mahmoudalyudeen.plutus.api

import com.mahmoudalyudeen.plutus.db.entities.DatabaseBitcoinValue

data class MarketPriceDto(
    val description: String,
    val name: String,
    val period: String,
    val status: String,
    val unit: String,
    val values: List<ValueDto>
) {
    data class ValueDto(
        val x: Long,
        val y: Double
    )
}

fun MarketPriceDto.asDatabaseBitcoinValues() = values.map { it.asDatabaseBitcoinValue() }

fun MarketPriceDto.ValueDto.asDatabaseBitcoinValue() = DatabaseBitcoinValue(
    timestamp = x,
    value = y
)
