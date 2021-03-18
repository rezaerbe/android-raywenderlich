package com.erbe.potterverse

import android.app.Application

open class PotterApp : Application() {
  open fun getBaseUrl() = "https://hp-api.herokuapp.com/"
}