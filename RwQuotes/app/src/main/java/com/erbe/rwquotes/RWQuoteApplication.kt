package com.erbe.rwquotes

import android.app.Application
import timber.log.Timber

class RWQuoteApplication : Application(){

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
  }
}