package com.mahmoudalyudeen.plutus.di.data

import android.content.Context
import androidx.room.Room
import com.mahmoudalyudeen.plutus.db.BitcoinDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @JvmStatic
    @Provides
    fun providesDatabase(context: Context): BitcoinDatabase =
        Room.databaseBuilder(context, BitcoinDatabase::class.java, "bitcoindatabase").build()
}