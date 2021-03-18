package com.erbe.ingredisearch.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erbe.ingredisearch.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    searchButton.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
    favButton.setOnClickListener { startActivity(Intent(this, FavoritesActivity::class.java)) }
  }
}
