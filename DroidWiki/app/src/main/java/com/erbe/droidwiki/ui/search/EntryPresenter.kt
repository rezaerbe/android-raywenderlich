package com.erbe.droidwiki.ui.search

interface EntryPresenter {

    fun setView(entryView: EntryView)

    fun getEntry(query: String)
}