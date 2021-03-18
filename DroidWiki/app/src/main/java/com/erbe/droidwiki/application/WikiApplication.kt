package com.erbe.droidwiki.application

import android.app.Application
import com.erbe.droidwiki.dagger.AppComponent
import com.erbe.droidwiki.dagger.AppModule
import com.erbe.droidwiki.dagger.DaggerAppComponent

class WikiApplication : Application() {

    lateinit var wikiComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        wikiComponent = initDagger(this)
    }

    private fun initDagger(app: WikiApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}