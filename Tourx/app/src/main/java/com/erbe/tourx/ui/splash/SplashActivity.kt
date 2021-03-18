package com.erbe.tourx.ui.splash

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.erbe.tourx.R
import com.erbe.tourx.entities.Cost
import com.erbe.tourx.entities.Place
import com.erbe.tourx.ui.BaseActivity
import com.erbe.tourx.ui.placelist.PlaceListActivity
import com.erbe.tourx.utils.LOG_TAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<Nothing>() {

    private val gson = Gson()
    private val resource: Resources
        get() = this.resources

    private val viewModel: SplashViewModel by viewModels()

    private val costs: List<Cost>
        get() {
            val json = resource.openRawResource(R.raw.prices).bufferedReader().use { it.readText() }
            return gson.fromJson(json, object : TypeToken<List<Cost>>() {}.type)
        }

    private val places: List<Place>
        get() {
            val json = resource.openRawResource(R.raw.places).bufferedReader().use { it.readText() }
            return gson.fromJson(json, object : TypeToken<List<Place>>() {}.type)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.activity_splash)

        viewModel.populateData(places = places, costs = costs)
        viewModel.result.observe(this, this)
    }

    override fun onDataLoaded(data: Nothing) {
        Log.i(LOG_TAG, "Populating db...")
    }

    override fun onTaskComplete() {
        startActivity(Intent(this, PlaceListActivity::class.java))
        finish()
    }

    override fun onError(message: String) {
        Log.e(LOG_TAG, message)
        Toast.makeText(this@SplashActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }
}