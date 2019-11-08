package com.mahmoudalyudeen.plutus.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahmoudalyudeen.plutus.db.entities.DatabaseBitcoinValue

@Dao
interface BitcoinValueDao {

    @Query("select * from databasebitcoinvalue")
    fun getBitcoinValues(): LiveData<List<DatabaseBitcoinValue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBitcoinValues(values: List<DatabaseBitcoinValue>)

}

@Database(entities = [DatabaseBitcoinValue::class], version = 1)
abstract class BitcoinDatabase : RoomDatabase() {
    abstract val bitcoinValueDao: BitcoinValueDao
}
