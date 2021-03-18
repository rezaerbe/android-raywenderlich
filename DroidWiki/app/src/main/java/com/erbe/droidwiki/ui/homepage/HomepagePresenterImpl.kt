package com.erbe.droidwiki.ui.homepage

import com.erbe.droidwiki.model.HomepageResult
import com.erbe.droidwiki.network.Homepage
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HomepagePresenterImpl @Inject constructor(private val homepage: Homepage) : HomepagePresenter {

    private lateinit var homepageView: HomepageView

    override fun setView(homepageView: HomepageView) {
        this.homepageView = homepageView
    }

    override fun loadHomepage() {
        homepageView.displayLoading()
        homepage.get().enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                homepageView.dismissLoading()
                if (response.isSuccessful) {
                    response.let {
                        HomepageResult(it).homepage?.let {
                            homepageView.displayHomepage(it)
                        } ?: run {
                            homepageView.displayError(response.message)
                        }
                    }
                } else {
                    homepageView.displayError(response.message)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                homepageView.displayError(e.message)
                e.printStackTrace()
            }
        })
    }
}