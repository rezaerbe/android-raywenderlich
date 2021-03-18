package com.erbe.droidwiki.ui.search

import com.erbe.droidwiki.model.SearchResult
import com.erbe.droidwiki.network.Wiki
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class EntryPresenterImpl @Inject constructor(private val wiki: Wiki) : EntryPresenter {

    private lateinit var entryView: EntryView

    override fun setView(entryView: EntryView) {
        this.entryView = entryView
    }

    override fun getEntry(query: String) {
        entryView.displayLoading()
        wiki.search(query).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                entryView.dismissLoading()
                //Everything is ok, show the result if not null
                if (response.isSuccessful) {
                    SearchResult(response).list?.let {
                        entryView.displayEntries(it)
                    }
                } else {
                    entryView.displayError(response.message)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                entryView.displayError(e.message)
                e.printStackTrace()
            }
        })

    }
}