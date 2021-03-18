package com.erbe.handbook.ui

import android.content.Intent
import android.os.Bundle

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (handsDb.getLoggedInHand() != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }

        finish()
    }
}