package com.erbe.rwdc.app

import android.app.Application
import android.content.Context

class RWDCApplication : Application() {

    companion object {
        private lateinit var instance: RWDCApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}