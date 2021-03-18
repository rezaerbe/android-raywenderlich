package com.erbe.githubrepolist.data

import com.google.gson.Gson
import java.net.URL

class Request() {

    companion object {
        private val URL = "https://api.github.com/search/repositories"
        private val SEARCH = "q=language:kotlin&sort=stars&order=desc"
        private val COMPLETE_URL = "$URL?$SEARCH"
    }

    fun run(): RepoResult {
        val repoListJsonStr = URL(COMPLETE_URL).readText()
        return Gson().fromJson(repoListJsonStr, RepoResult::class.java)
    }
}