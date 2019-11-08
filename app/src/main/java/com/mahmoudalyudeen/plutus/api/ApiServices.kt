package com.mahmoudalyudeen.plutus.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.blockchain.info/"

class ApiServices {

    interface BitcoinApi {

        @GET("charts/market-price?timespan=1year&format=json")
        suspend fun getMarketPrice(): MarketPriceDto

    }

    fun createBitcoinApi(): BitcoinApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().apply { addNetworkInterceptor(interceptor) }.build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retroFit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

        return retroFit.create(BitcoinApi::class.java)
    }
}

enum class CallStatus { Loading, Error, Idle, Empty }