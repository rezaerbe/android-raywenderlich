package com.erbe.handbook.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erbe.handbook.R
import com.erbe.handbook.db.HandsDB

open class BaseActivity : AppCompatActivity() {

    protected lateinit var handsDb: HandsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handsDb = HandsDB(this)
    }

    //DONE: 7 Add greet method with same signature
    fun greet() {
        Toast.makeText(this, getString(R.string.welcome_base_activity_member),
            Toast.LENGTH_SHORT).show()
    }
}