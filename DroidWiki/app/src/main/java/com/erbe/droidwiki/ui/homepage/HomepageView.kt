package com.erbe.droidwiki.ui.homepage

import com.erbe.droidwiki.model.WikiHomepage

interface HomepageView {

    fun displayLoading()

    fun dismissLoading()

    fun displayHomepage(result: WikiHomepage)

    fun displayError(error: String?)
}