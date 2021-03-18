package com.erbe.droidwiki.model

import okhttp3.Response
import org.json.JSONObject

data class HomepageResult(private val response: Response) {

    var homepage: WikiHomepage? = null

    init {
        homepage = response.body?.string()?.let {
            JSONObject(it)
                .getJSONObject("parse")
                .getJSONObject("text")
                .getString("*")
                .let {
                    WikiHomepage(it)
                }
        }
    }
}