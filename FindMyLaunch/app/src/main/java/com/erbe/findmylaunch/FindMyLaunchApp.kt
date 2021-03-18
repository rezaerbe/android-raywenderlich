package com.erbe.findmylaunch

import android.app.Application
import android.content.Context
import com.erbe.findmylaunch.di.AppComponent
import com.erbe.findmylaunch.di.DaggerAppComponent

class FindMyLaunchApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

fun appComponent(context: Context): AppComponent {
    val app = context.applicationContext as FindMyLaunchApp
    return app.appComponent
}