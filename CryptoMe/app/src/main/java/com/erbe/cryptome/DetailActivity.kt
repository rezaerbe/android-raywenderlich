package com.erbe.cryptome

import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupToolBar()
        setupStatusBar()
    }

    private fun loadWebView(crytoType: String) {
        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        webView.loadUrl("https://www.tradingview.com/symbols/${crytoType.toUpperCase()}USD/")
    }

    private fun setupToolBar() {
        val cryptoName = intent.getStringExtra("CryptoName")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = cryptoName

        if (cryptoName != null) {
            loadWebView(cryptoName)
        }
    }

    private fun setupStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }
}