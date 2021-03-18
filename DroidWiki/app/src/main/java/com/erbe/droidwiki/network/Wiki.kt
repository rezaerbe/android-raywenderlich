package com.erbe.droidwiki.network

class Wiki(private val api: WikiApi) {
    fun search(query: String) = api.search(query)
}