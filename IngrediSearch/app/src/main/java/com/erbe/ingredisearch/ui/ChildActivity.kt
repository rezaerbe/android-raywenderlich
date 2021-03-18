package com.erbe.ingredisearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ChildActivity : AppCompatActivity() {

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

}