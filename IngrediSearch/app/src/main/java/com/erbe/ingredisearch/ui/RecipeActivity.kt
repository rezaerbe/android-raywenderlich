package com.erbe.ingredisearch.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import com.erbe.ingredisearch.R
import kotlinx.android.synthetic.main.activity_recipe.*

fun Context.recipeIntent(url: String): Intent {
  return Intent(this, RecipeActivity::class.java).apply {
    putExtra(EXTRA_URL, url)
  }
}

private const val EXTRA_URL = "EXTRA_URL"

class RecipeActivity : ChildActivity() {

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe)

    val url = intent.getStringExtra(EXTRA_URL).toString().replace("http", "https")

    val webSettings = webView.settings
    webSettings.javaScriptEnabled = true
    webView.isHorizontalScrollBarEnabled = false
    webView.loadUrl(url)
    webView.webViewClient = object : WebViewClient() {}
  }
}
