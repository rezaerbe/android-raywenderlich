package com.erbe.droidwiki.ui.search

import com.erbe.droidwiki.model.Entry

interface EntryView {

    fun displayLoading()

    fun dismissLoading()

    fun displayEntries(results: List<Entry>)

    fun displayError(error: String?)
}