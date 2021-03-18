package com.erbe.droidwiki.ui.homepage

interface HomepagePresenter {

    fun setView(homepageView: HomepageView)

    fun loadHomepage()
}