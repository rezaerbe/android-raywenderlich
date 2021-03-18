package com.erbe.sunzoid.application

import android.app.Application
import com.erbe.sunzoid.di.applicationModule
import com.erbe.sunzoid.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SunzoidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@SunzoidApplication)
            modules(listOf(applicationModule, presentationModule))
        }
    }
}