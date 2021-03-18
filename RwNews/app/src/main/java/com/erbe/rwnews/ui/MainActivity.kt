package com.erbe.rwnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erbe.rwnews.R
import com.erbe.rwnews.ui.list.NewsListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // HERE
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.anchor, NewsListFragment())
          .commit()
    }
  }
}

