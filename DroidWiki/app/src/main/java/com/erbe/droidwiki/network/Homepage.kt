package com.erbe.droidwiki.network

class Homepage(val api: WikiApi) {
    fun get() = api.getHomepage()
}