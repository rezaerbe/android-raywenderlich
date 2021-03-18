package com.erbe.cheesefinder

import android.app.Application
import com.facebook.stetho.Stetho

class CheeseApp: Application(){

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}