package com.mahmoudalyudeen.plutus.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudalyudeen.plutus.domain.BitcoinValue

@Entity
data class DatabaseBitcoinValue(
    @PrimaryKey
    val timestamp: Long,
    val value: Double
)

fun List<DatabaseBitcoinValue>.asDomainBitcoinValues() = map {
    BitcoinValue(
        timestamp = it.timestamp,
        value = it.value
    )
}
