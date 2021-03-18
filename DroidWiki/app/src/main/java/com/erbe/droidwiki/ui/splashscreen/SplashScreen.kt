package com.erbe.droidwiki.ui.splashscreen

import android.app.Activity
import android.os.Bundle
import com.erbe.droidwiki.ui.homepage.HomepageActivity
import com.erbe.droidwiki.utils.start

class Splashscreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomepageActivity::class.start(this, true)
    }
}