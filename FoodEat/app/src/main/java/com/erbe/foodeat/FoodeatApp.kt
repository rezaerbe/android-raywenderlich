package com.erbe.foodeat

import android.app.Application
import com.erbe.foodeat.injection.restaurantModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FoodeatApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FoodeatApp)
            modules(restaurantModule)
        }
    }
}