package com.erbe.bmicalc

import android.app.Application
import android.content.Context
import com.erbe.bmicalc.model.Repository

class BMIApplication : Application() {
    val repository: Repository by lazy {
        Repository(getSharedPreferences("BMIApplication", Context.MODE_PRIVATE))
    }
}