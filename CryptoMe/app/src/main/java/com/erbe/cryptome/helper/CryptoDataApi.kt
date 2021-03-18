package com.erbe.cryptome.helper

import com.google.gson.internal.LinkedTreeMap
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val APIKEY = "APIKEY" // TODO: Add Your Register API Key Here
const val BASEURL = "https://min-api.cryptocompare.com/"

interface CryptoDataAPI {
    @Headers("Authorization: $APIKEY")
    @GET("data/pricemulti?fsyms=BTC,ETH,LTC")
    fun getCryptoData(@Query("tsyms") currencies: String): Observable<LinkedTreeMap<Any, Any>>
}