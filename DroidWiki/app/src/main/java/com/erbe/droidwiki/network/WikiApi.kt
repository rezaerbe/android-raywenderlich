package com.erbe.droidwiki.network

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class WikiApi @Inject constructor(private val client: OkHttpClient, private val requestBuilder: HttpUrl.Builder?) {

    fun search(query: String): Call {
        val urlBuilder = requestBuilder
            ?.addQueryParameter("action", "query")
            ?.addQueryParameter("list", "search")
            ?.addQueryParameter("format", "json")
            ?.addQueryParameter("srsearch", query)

        return Request.Builder()
            .url(urlBuilder!!.build())
            .get()
            .build()
            .let {
                client.newCall(it)
            }

    }

    fun getHomepage(): Call {
        val urlBuilder = requestBuilder
            ?.addQueryParameter("action", "parse")
            ?.addQueryParameter("page", "Main Page")
            ?.addQueryParameter("format", "json")

        return Request.Builder()
            .url(urlBuilder!!.build())
            .get()
            .build()
            .let {
                client.newCall(it)
            }

    }
}