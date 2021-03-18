package com.erbe.droidwiki.model

import okhttp3.Response
import org.json.JSONObject

data class SearchResult(private val response: Response) {

    var list: List<Entry>? = listOf()

    init {
        list = response.body?.string()?.let {
            JSONObject(it)
                .getJSONObject("query")
                .getJSONArray("search")
                .let { array ->
                    (0 until array.length()).map {
                        array.getJSONObject(it)
                    }.map {
                        Entry(it.getString("title"), it.getString("snippet"))
                    }
                }
        }
    }
}