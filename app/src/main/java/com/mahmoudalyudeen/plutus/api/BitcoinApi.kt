package com.mahmoudalyudeen.plutus.api

import retrofit2.http.GET

interface BitcoinApi {

    @GET("charts/market-price?timespan=1year&format=json")
    suspend fun getMarketPrice(): MarketPriceDto

}